package home.vertx.pdf

import io.vertx.core.Handler
import io.vertx.core.buffer.impl.BufferImpl
import io.vertx.ext.web.RoutingContext
import org.xhtmlrenderer.pdf.ITextRenderer

class NonBlockingLocalTemplatePdfHandler implements Handler<RoutingContext> {

    @Override
    void handle(RoutingContext ctx) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        String body = NonBlockingLocalTemplatePdfHandler.class.classLoader.getResourceAsStream("invoice.html").getText("utf-8")
        try {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString( body );
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

    private void renderPdf(String body, OutputStream outputStream) {
        renderer.setDocumentFromString( body );
        renderer.layout()
        renderer.createPDF(outputStream)
        outputStream.close()
    }
}
