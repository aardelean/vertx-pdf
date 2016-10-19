package home.vertx.pdf

import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpServer
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.impl.BodyHandlerImpl

class PdfVerticle extends AbstractVerticle {

    @Override
    void start() throws Exception {
        Router router = Router.router(vertx)
        router.route().handler(BodyHandlerImpl.create())
        router.post("/pdf").handler(new NonBlockingPdfHandler())
        router.post("/blocking-pdf").handler(new BlockingPdfHandler())
        router.get("/blocking-local-pdf").handler(new BlockingLocalTemplatePdfHandler())
        router.get("/local-pdf").handler(new NonBlockingLocalTemplatePdfHandler())
        HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(router.&accept);
        httpServer.listen(8888);
    }
}
