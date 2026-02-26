package uva.c1.g1.p1721;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

enum Direction {
    LEFT, RIGHT, TOP, BOTTOM, NONE;

    public static Direction of(int dx, int dy) {
        return dx < 0 ? LEFT : dx > 0 ? RIGHT : dy < 0 ? TOP : dy > 0 ? BOTTOM : NONE;
    }
}

interface Snapshot<T> {
    T snapshot();

    void restore(T snapshot);
}

abstract class InternalError extends Exception {
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

/**
 * 1721 - Window Manager
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=4794
 */
public class Main {
    public static void main(String... args) throws IOException {
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

            Window[] windows = screen.windows();
            out.format("%d window(s):\n", windows.length);
            for (Window w : windows) out.format("%d %d %d %d\n", w.x(), w.y(), w.width(), w.height());
        }

        in.close();
        out.flush();
        out.close();
    }
}

final class Transaction<T extends Snapshot<T>> {
    private final T object;
    private T snapshot;

    public Transaction(T object) {
        this.object = object;
        this.snapshot = null;
    }

    public void start() {
        if (snapshot == null) snapshot = object.snapshot();
    }

    public void commit() {
        snapshot = null;
    }

    public void rollback() {
        if (committed()) return;
        object.restore(snapshot);
        commit();
    }

    public T read(boolean committed) {
        return !committed || committed() ? object : snapshot;
    }

    public boolean committed() {
        return snapshot == null;
    }
}

final class Screen {
    private final ArrayList<Window> windows = new ArrayList<>(256);
    private final int width;
    private final int height;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Window[] windows() {
        return windows.toArray(new Window[0]);
    }

    public void open(int x, int y, int w, int h) throws InternalError {
        Window created = new Window(x, y, w, h);
        if (outside(created) || overlap(created)) throw new UnfitWindowError();
        windows.add(created);
    }

    public void close(int x, int y) throws InternalError {
        Window closed = target(x, y);
        windows.remove(closed);
    }

    public void resize(int x, int y, int w, int h) throws InternalError {
        Window resized = target(x, y);
        resized.transaction.start();
        resized.resize(w, h);
        if (outside(resized) || overlap(resized)) {
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
        Window moved = target(x, y);

        forcePush(moved, dx, dy);
        pullToScreen(changes(), dx, dy);

        int actualDx = moved.dx(), actualDy = moved.dy();
        windows.forEach(w -> w.transaction.commit());

        if (actualDx != dx) throw new UnexpectedMoveError(dx, actualDx);
        if (actualDy != dy) throw new UnexpectedMoveError(dy, actualDy);
    }

    private void forcePush(Window moved, int dx, int dy) {
        Window movement = moved.snapshot().stretch(dx, dy);

        // push collided windows
        for (Window existing : windows) {
            boolean collided = existing != moved && existing.overlap(movement);
            if (!collided) continue;

            int leftDx = movement.left() - existing.right(), rightDx = movement.right() - existing.left();
            int topDy = movement.top() - existing.bottom(), bottomDy = movement.bottom() - existing.top();
            int newDx = dx < 0 ? leftDx : dx > 0 ? rightDx : 0;
            int newDy = dy < 0 ? topDy : dy > 0 ? bottomDy : 0;
            forcePush(existing, newDx, newDy);
        }

        // push window
        moved.transaction.start();
        moved.move(dx, dy);
    }

    private void pullToScreen(List<Window> targets, int dx, int dy) {
        int minLeft = targets.stream().mapToInt(Window::left).min().orElse(0);
        int maxRight = targets.stream().mapToInt(Window::right).max().orElse(0);
        int minTop = targets.stream().mapToInt(Window::top).min().orElse(0);
        int maxBottom = targets.stream().mapToInt(Window::bottom).max().orElse(0);
        int pullDx = minLeft < 0 ? -minLeft : maxRight > width ? width - maxRight : 0;
        int pullDy = minTop < 0 ? -minTop : maxBottom > height ? height - maxBottom : 0;
        targets.forEach(t -> t.move(pullDx, pullDy));

        Direction direction = Direction.of(dx, dy);
        targets.stream().filter(t -> !direction.equals(t.direction())).forEach(t -> t.transaction.rollback());
    }

    private List<Window> changes() {
        return windows.stream().filter(w -> !w.transaction.committed()).collect(Collectors.toList());
    }

    private Window target(int x, int y) throws NoWindowError {
        return windows.stream().filter(w -> w.overlap(x, y)).findFirst().orElseThrow(NoWindowError::new);
    }

    private boolean overlap(Window target) {
        return windows.stream().anyMatch(existing -> existing != target && existing.overlap(target));
    }

    private boolean outside(Window window) {
        return (window.left() < 0 || window.right() > width || window.top() < 0 || window.bottom() > height);
    }
}

abstract class ImmutableWindow {
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public ImmutableWindow(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public int left() {
        return x();
    }

    public int right() {
        return x() + width();
    }

    public int top() {
        return y();
    }

    public int bottom() {
        return y() + height();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ImmutableWindow)) return false;
        ImmutableWindow o = (ImmutableWindow) object;
        return x() == o.x() && y() == o.y() && width() == o.width() && height() == o.height();
    }

    public boolean overlap(Window o) {
        boolean onTop = bottom() <= o.top();
        boolean onBottom = top() >= o.bottom();
        boolean onLeft = right() <= o.left();
        boolean onRight = left() >= o.right();
        return !onTop && !onBottom && !onLeft && !onRight;
    }

    public boolean overlap(int x, int y) {
        return left() <= x && x < right() && top() <= y && y < bottom();
    }
}

final class Window extends ImmutableWindow implements Snapshot<Window> {
    public final Transaction<Window> transaction;

    public Window(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.transaction = new Transaction<>(this);
    }

    @Override
    public Window snapshot() {
        return new Window(x(), y(), width(), height());
    }

    @Override
    public void restore(Window snapshot) {
        x = snapshot.x();
        y = snapshot.y();
        width = snapshot.width();
        height = snapshot.height();
    }

    public Window stretch(int dx, int dy) {
        x = Math.min(x, x + dx);
        y = Math.min(y, y + dy);
        width += Math.abs(dx);
        height += Math.abs(dy);
        return this;
    }

    public Window move(int dx, int dy) {
        x += dx;
        y += dy;
        return this;
    }

    public Window resize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public Direction direction() {
        return Direction.of(dx(), dy());
    }

    public int dx() {
        return transaction.read(false).x() - transaction.read(true).x();
    }

    public int dy() {
        return transaction.read(false).y() - transaction.read(true).y();
    }
}
