package home.vertx.pdf

import io.vertx.core.Handler
import io.vertx.core.buffer.impl.BufferImpl
import io.vertx.ext.web.RoutingContext
import org.xhtmlrenderer.pdf.ITextRenderer

class NonBlockingPdfHandler implements Handler<RoutingContext> {
    ITextRenderer renderer = new ITextRenderer();
    @Override
    void handle(RoutingContext ctx) {
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream()
            renderer.setDocumentFromString( ctx.bodyAsString );
            renderer.layout()
            renderer.createPDF(baos)
            byte[] content = baos.toByteArray()
            ctx.response()
                    .putHeader("Content-Type", "application/pdf")
                    .putHeader("Content-Length", "" + content.length)
                    .end(new BufferImpl(content))
        } catch(Exception e){
            ctx.response().setStatusCode(500).end(e.cause().toString())
        }
    }
}

