package dev.rizaldi.uhunt.c1.p1721;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


/**
 * 1721 - Window Manager
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=4794
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNextInt()) {
            int width = in.nextInt();
            int height = in.nextInt();
            Screen screen = new Screen(width, height);

            for (int i = 0; in.hasNext(); i++) {
                if (in.hasNextInt()) break;
                String action = in.next();
                try {
                    switch (action) {
                        case "OPEN":
                            screen.open(in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt());
                            break;
                        case "CLOSE":
                            screen.close(in.nextInt(), in.nextInt());
                            break;
                        case "RESIZE":
                            screen.resize(in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt());
                            break;
                        case "MOVE":
                            screen.move(in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt());
                            break;
                    }
                } catch (InternalError e) {
                    out.format("Command %d: %s - %s\n", i + 1, action, e.getMessage());
                }
            }

            MutableWindow[] mutableWindows = screen.windows();
            out.format("%d window(s):\n", mutableWindows.length);
            for (MutableWindow w : mutableWindows) out.format("%d %d %d %d\n", w.x(), w.y(), w.width(), w.height());
        }

        in.close();
        out.flush();
        out.close();
    }
}

class InternalError extends Exception {
    public InternalError(String message) {
        super(message);
    }
}

final class NoWindowError extends InternalError {
    public NoWindowError() {
        super("no window at given position");
    }
}

final class UnfitWindowError extends InternalError {
    public UnfitWindowError() {
        super("window does not fit");
    }
}

final class UnexpectedMoveError extends InternalError {
    public UnexpectedMoveError(int expected, int actual) {
        super(String.format("moved %d instead of %d", Math.abs(actual), Math.abs(expected)));
    }
}

class Transaction<T> {
    private T committed;
    private T uncommitted;

    public Transaction(T initial) {
        this.committed = initial;
        this.uncommitted = null;
    }

    public void update(T updated) {
        this.uncommitted = updated;
    }

    public boolean uncommitted() {
        return uncommitted != null;
    }

    public void commit() {
        committed = uncommitted == null ? committed : uncommitted;
        uncommitted = null;
    }

    public void rollback() {
        this.uncommitted = null;
    }

    public T read(boolean committed) {
        return committed || this.uncommitted == null ? this.committed : this.uncommitted;
    }
}

final class Screen {
    private final ArrayList<MutableWindow> windows = new ArrayList<>(256);
    private final int width;
    private final int height;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public MutableWindow[] windows() {
        return windows.toArray(new MutableWindow[0]);
    }

    public void open(int x, int y, int w, int h) throws InternalError {
        MutableWindow created = new MutableWindow(x, y, w, h);

        if (outside(created)) throw new UnfitWindowError();
        if (overlap(created)) throw new UnfitWindowError();

        windows.add(created);
    }

    public void close(int x, int y) throws InternalError {
        MutableWindow closed = target(x, y);
        windows.remove(closed);
    }

    public void resize(int x, int y, int w, int h) throws InternalError {
        MutableWindow resized = target(x, y);
        resized.resize(w, h);

        if (outside(resized)) {
            resized.transaction.rollback();
            throw new UnfitWindowError();
        }
        if (overlap(resized)) {
            resized.transaction.rollback();
            throw new UnfitWindowError();
        }

        resized.transaction.commit();
    }

    /**
     * step to move window:
     * - force push windows recursively (ignoring the screen size)
     * - pull pushed windows to inside the screen
     * - if pull too much (exceed original position), use original instead (in other words, it's not pushed)
     */
    public void move(int x, int y, int dx, int dy) throws InternalError {
        MutableWindow moved = target(x, y);

        forcePush(moved, dx, dy);
        pullToScreen(dx, dy);

        int actualDx = moved.dx(), actualDy = moved.dy();
        windows.forEach(w -> w.transaction.commit());

        if (actualDx != dx) throw new UnexpectedMoveError(dx, actualDx);
        if (actualDy != dy) throw new UnexpectedMoveError(dy, actualDy);
    }

    private void forcePush(MutableWindow moved, int dx, int dy) {
        Window movement = moved.movement(dx, dy);

        for (MutableWindow existing : windows) {
            if (existing == moved) continue;
            if (!existing.overlap(movement)) continue;

            int leftDx = movement.left() - existing.right(), rightDx = movement.right() - existing.left();
            int topDy = movement.top() - existing.bottom(), bottomDy = movement.bottom() - existing.top();
            int newDx = dx < 0 ? leftDx : dx > 0 ? rightDx : 0;
            int newDy = dy < 0 ? topDy : dy > 0 ? bottomDy : 0;
            forcePush(existing, newDx, newDy);
        }

        moved.move(dx, dy);
    }

    private void pullToScreen(int dx, int dy) {
        List<MutableWindow> changes = changes();
        if (dx < 0) pullLeft(changes);
        if (dx > 0) pullRight(changes);
        if (dy < 0) pullTop(changes);
        if (dy > 0) pullBottom(changes);
    }

    private void pullLeft(List<MutableWindow> changes) {
        int minLeft = changes.stream().mapToInt(MutableWindow::left).min().orElse(0);
        int moveRight = minLeft < 0 ? -minLeft : 0;
        if (moveRight == 0) return;

        changes.forEach(c -> c.move(moveRight, 0));
        changes.stream().filter(w -> w.dx() > 0).forEach(c -> c.transaction.rollback());
    }

    private void pullRight(List<MutableWindow> changes) {
        int maxRight = changes.stream().mapToInt(MutableWindow::right).max().orElse(width);
        int moveLeft = maxRight > width ? width - maxRight : 0;
        if (moveLeft == 0) return;

        changes.forEach(c -> c.move(moveLeft, 0));
        changes.stream().filter(w -> w.dx() < 0).forEach(w -> w.transaction.rollback());
    }

    private void pullTop(List<MutableWindow> changes) {
        int minTop = changes.stream().mapToInt(MutableWindow::top).min().orElse(0);
        int moveBottom = minTop < 0 ? -minTop : 0;
        if (moveBottom == 0) return;

        changes.forEach(c -> c.move(0, moveBottom));
        changes.stream().filter(w -> w.dy() > 0).forEach(w -> w.transaction.rollback());
    }

    private void pullBottom(List<MutableWindow> changes) {
        int maxBottom = changes.stream().mapToInt(MutableWindow::bottom).max().orElse(height);
        int moveUp = maxBottom > height ? height - maxBottom : 0;
        if (moveUp == 0) return;

        changes.forEach(c -> c.move(0, moveUp));
        changes.stream().filter(w -> w.dy() < 0).forEach(w -> w.transaction.rollback());
    }

    private List<MutableWindow> changes() {
        return windows.stream().filter(w -> w.transaction.uncommitted()).collect(Collectors.toList());
    }

    private MutableWindow target(int x, int y) throws NoWindowError {
        return windows.stream().filter(mutableWindow -> mutableWindow.overlap(x, y)).findFirst().orElseThrow(NoWindowError::new);
    }

    private boolean overlap(MutableWindow target) {
        return windows.stream().anyMatch(existing -> existing != target && existing.overlap(target));
    }

    private boolean outside(MutableWindow mutableWindow) {
        return (mutableWindow.left() < 0 || mutableWindow.right() > width || mutableWindow.top() < 0 || mutableWindow.bottom() > height);
    }
}

abstract class Window {
    abstract public int x();

    abstract public int y();

    abstract public int width();

    abstract public int height();

    public final int left() {
        return x();
    }

    public final int right() {
        return x() + width();
    }

    public final int top() {
        return y();
    }

    public final int bottom() {
        return y() + height();
    }

    @Override
    public final boolean equals(Object object) {
        if (!(object instanceof Window)) return false;
        Window o = (Window) object;
        return x() == o.x() && y() == o.y() && width() == o.width() && height() == o.height();
    }

    public final boolean overlap(Window o) {
        boolean onTop = bottom() <= o.top();
        boolean onBottom = top() >= o.bottom();
        boolean onLeft = right() <= o.left();
        boolean onRight = left() >= o.right();
        return !onTop && !onBottom && !onLeft && !onRight;
    }

    public final boolean overlap(int x, int y) {
        return left() <= x && x < right() && top() <= y && y < bottom();
    }

    public final Window movement(int dx, int dy) {
        int x = Math.min(x(), x() + dx);
        int y = Math.min(y(), y() + dy);
        int width = width() + Math.abs(dx);
        int height = height() + Math.abs(dy);
        return new ImmutableWindow(x, y, width, height);
    }
}

final class ImmutableWindow extends Window {
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public ImmutableWindow(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public int x() {
        return x;
    }

    @Override
    public int y() {
        return y;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }
}

final class MutableWindow extends Window {
    public Transaction<ImmutableWindow> transaction;

    public MutableWindow(int x, int y, int width, int height) {
        ImmutableWindow w = new ImmutableWindow(x, y, width, height);
        transaction = new Transaction<>(w);
    }

    @Override
    public int x() {
        return transaction.read(false).x();
    }

    @Override
    public int y() {
        return transaction.read(false).y();
    }

    @Override
    public int width() {
        return transaction.read(false).width();
    }

    @Override
    public int height() {
        return transaction.read(false).height();
    }

    public void move(int dx, int dy) {
        transaction.update(new ImmutableWindow(x() + dx, y() + dy, width(), height()));
    }

    public void resize(int width, int height) {
        transaction.update(new ImmutableWindow(x(), y(), width, height));
    }

    public int dx() {
        return transaction.read(false).x() - transaction.read(true).x();
    }

    public int dy() {
        return transaction.read(false).y() - transaction.read(true).y();
    }
}
