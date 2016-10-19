package home.vertx.pdf

import io.vertx.core.Handler
import io.vertx.core.buffer.impl.BufferImpl
import io.vertx.ext.web.RoutingContext
import org.xhtmlrenderer.pdf.ITextRenderer

class BlockingPdfHandler implements Handler<RoutingContext> {
    ITextRenderer renderer = new ITextRenderer();
    @Override
    void handle(RoutingContext ctx) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ctx.vertx().executeBlocking({future ->
            renderPdf(ctx.bodyAsString, baos)
            future.complete();
        }, {res ->
            if (res.succeeded()) {
                byte[] content = baos.toByteArray()
                ctx.response()
                        .putHeader("Content-Type", "application/pdf")
                        .putHeader("Content-Length", "" + content.length)
                        .end(new BufferImpl(content))
            } else {
                res.cause().printStackTrace();
                ctx.response().setStatusCode(500).end(res.cause().toString())
            }
        });
    }

    private void renderPdf(String body, OutputStream outputStream) {
        renderer.setDocumentFromString( body );
        renderer.layout()
        renderer.createPDF(outputStream)
        outputStream.close()
    }
}
