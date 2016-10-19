package home.vertx

import home.vertx.pdf.PdfVerticle
import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory

import java.text.NumberFormat

class Application {
    static final Logger log = LoggerFactory.getLogger(Application)
    static void main(String[] args) {
        int port = Integer.parseInt(System.getenv("PORT"));
        log.info "Starting on " + port
        DeploymentOptions options = new DeploymentOptions().setInstances(16);
        VertxOptions vertxOptions = new VertxOptions()
        vertxOptions.blockedThreadCheckInterval = 60000000000
        Vertx.vertx(vertxOptions).deployVerticle(PdfVerticle.class.getCanonicalName(), options);
        Runtime runtime = Runtime.getRuntime();
        final NumberFormat format = NumberFormat.getInstance();
        final long maxMemory = runtime.maxMemory();
        final long allocatedMemory = runtime.totalMemory();
        final long freeMemory = runtime.freeMemory();
        final long mb = 1024 * 1024;
        final String mega = " MB";
        log.info("========================== Memory Info ==========================");
        log.info("Free memory: " + format.format(freeMemory / mb) + mega);
        log.info("Allocated memory: " + format.format(allocatedMemory / mb) + mega);
        log.info("Max memory: " + format.format(maxMemory / mb) + mega);
        log.info("Total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / mb) + mega);
        log.info("=================================================================\n");
    }
}
