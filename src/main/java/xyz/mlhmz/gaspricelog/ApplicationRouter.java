package xyz.mlhmz.gaspricelog;

import io.quarkus.runtime.StartupEvent;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import jakarta.enterprise.event.Observes;

import java.util.ArrayList;
import java.util.List;

/**
 * Routes all non-API requests to the webapp.
 */
public class ApplicationRouter {
    private static final List<String> PATH_PREFIXES = List.of("/q/", "/api/", "/@", "/index.html", "/assets");
    private static final List<String> STATIC_FILES = List.of(
            "/android-chrome-192x192.png",
            "/android-chrome-512x512.png",
            "/apple-touch-icon.png",
            "/favicon.ico",
            "/favicon-16x16.png",
            "/favicon-32x32.png",
            "/site.webmanifest"
    );

    void installRoute(@Observes StartupEvent startupEvent, Router router) {
        StaticHandler distHandler = StaticHandler.create("META-INF/resources/");
        StaticHandler assetsHandler = StaticHandler.create("META-INF/resources/assets/");

        router.route().path("/").handler(distHandler);
        router.route().path("/*").handler(rc -> {
            final String path = rc.normalizedPath();

            List<String> excludedPaths = new ArrayList<>();
            excludedPaths.addAll(PATH_PREFIXES);
            excludedPaths.addAll(STATIC_FILES);
            if (!path.equals("/") && excludedPaths.stream().noneMatch(path::startsWith)) {
                rc.reroute("/index.html");
            } else if (STATIC_FILES.contains(path)) {
                distHandler.handle(rc);
            } else if (path.equals("/index.html")) {
                distHandler.handle(rc);
            } else {
                rc.next();
            }
        });
        router.route().path("/assets/*").handler(assetsHandler);


    }
}
