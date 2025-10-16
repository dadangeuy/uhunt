package uhunt.c1.p405;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * 405 - Message Routing
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=346
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        for (int i = 0; in.hasNextInt(); i++) {
            int totalMta = in.nextInt();
            LinkedList<Route> routes = new LinkedList<>();

            for (int j = 0; j < totalMta; j++) {
                String sourceMailTransferAgent = in.next();
                int totalRoutes = in.nextInt();

                for (int k = 0; k < totalRoutes; k++) {
                    String destinationMailTransferAgent = in.next();
                    String country = in.next();
                    String administrativeManagementDomain = in.next();
                    String privateManagementDomain = in.next();
                    String organization = in.next();

                    Recipient recipient = new Recipient(country, administrativeManagementDomain, privateManagementDomain, organization);
                    Route route = new Route(sourceMailTransferAgent, destinationMailTransferAgent, recipient);
                    routes.addLast(route);
                }
            }

            MessageHandlingSystem system = new MessageHandlingSystem(routes);

            out.format("Scenario # %d\n", i + 1);
            int totalMessage = in.nextInt();
            for (int j = 0; j < totalMessage; j++) {
                String sourceMailTransferAgent = in.next();
                String country = in.next();
                String administrativeManagementDomain = in.next();
                String privateManagementDomain = in.next();
                String organization = in.next();
                Recipient recipient = new Recipient(country, administrativeManagementDomain, privateManagementDomain, organization);

                try {
                    String destinationMailTransferAgent = system.deliverMessage(sourceMailTransferAgent, recipient);
                    out.format("%d -- delivered to %s\n", j + 1, destinationMailTransferAgent);
                } catch (InternalError e) {
                    out.format("%d -- %s\n", j + 1, e.getMessage());
                }
            }
            out.println();
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Recipient implements Comparable<Recipient> {
    public static final String ANY = "*";
    public final String country;
    public final String administrativeManagementDomain;
    public final String privateManagementDomain;
    public final String organization;

    public Recipient(String country, String administrativeManagementDomain, String privateManagementDomain, String organization) {
        this.country = country;
        this.administrativeManagementDomain = administrativeManagementDomain;
        this.privateManagementDomain = privateManagementDomain;
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Recipient)) return false;
        Recipient r = (Recipient) o;
        return compareTo(r) == 0;
    }

    @Override
    public int compareTo(Recipient o) {
        int c1 = compareTo(country, o.country);
        int c2 = compareTo(administrativeManagementDomain, o.administrativeManagementDomain);
        int c3 = compareTo(privateManagementDomain, o.privateManagementDomain);
        int c4 = compareTo(organization, o.organization);
        return c1 != 0 ? c1 : c2 != 0 ? c2 : c3 != 0 ? c3 : c4;
    }

    private int compareTo(String s1, String s2) {
        return s1.equals(ANY) ? 0 : s2.equals(ANY) ? 0 : s1.compareTo(s2);
    }
}

class Route {
    public final String sourceMailTransferAgent;
    public final String destinationMailTransferAgent;
    public final Recipient recipient;

    public Route(String sourceMailTransferAgent, String destinationMailTransferAgent, Recipient recipient) {
        this.sourceMailTransferAgent = sourceMailTransferAgent;
        this.destinationMailTransferAgent = destinationMailTransferAgent;
        this.recipient = recipient;
    }

    public boolean isLocal() {
        return sourceMailTransferAgent.equals(destinationMailTransferAgent);
    }
}

abstract class InternalError extends Exception {
    public InternalError(String message) {
        super(message);
    }
}

class CircularRoutingDetectedError extends InternalError {
    public CircularRoutingDetectedError(String mailTransferAgent) {
        super(String.format("circular routing detected by %s", mailTransferAgent));
    }
}

class UnableToRouteError extends InternalError {
    public UnableToRouteError(String mailTransferAgent) {
        super(String.format("unable to route at %s", mailTransferAgent));
    }
}

class MessageHandlingSystem {
    private final List<Route> routes;
    private final Map<String, List<Route>> routesPerSource;

    public MessageHandlingSystem(List<Route> routes) {
        this.routes = routes;
        this.routesPerSource = buildRoutesPerSource();
    }

    private Map<String, List<Route>> buildRoutesPerSource() {
        Map<String, List<Route>> routesPerSource = new HashMap<>();
        for (Route route : routes) {
            routesPerSource.computeIfAbsent(route.sourceMailTransferAgent, k -> new LinkedList<>()).add(route);
        }
        return routesPerSource;
    }

    public String deliverMessage(String sourceMailTransferAgent, Recipient recipient) throws InternalError {
        Set<String> visited = new HashSet<>();
        visited.add(sourceMailTransferAgent);

        while (true) {
            // find matching route
            List<Route> routes = routesPerSource.getOrDefault(sourceMailTransferAgent, Collections.emptyList());
            Route route = routes.stream().filter(r -> r.recipient.equals(recipient)).findFirst().orElse(null);
            if (route == null) throw new UnableToRouteError(sourceMailTransferAgent);

            // if local, message delivered
            if (route.isLocal()) return route.destinationMailTransferAgent;

            // check circular
            boolean circular = visited.contains(route.destinationMailTransferAgent);
            if (circular) {
                throw new CircularRoutingDetectedError(route.destinationMailTransferAgent);
            }

            // go to destination
            sourceMailTransferAgent = route.destinationMailTransferAgent;
            visited.add(sourceMailTransferAgent);
        }
    }
}
