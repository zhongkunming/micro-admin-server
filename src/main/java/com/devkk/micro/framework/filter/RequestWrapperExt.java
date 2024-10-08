package com.devkk.micro.framework.filter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author zhongkunming
 */
public class RequestWrapperExt extends ContentCachingRequestWrapper {

    private final AtomicBoolean hasRead = new AtomicBoolean(false);

    public RequestWrapperExt(HttpServletRequest request) {
        super(request);
    }

    public RequestWrapperExt(HttpServletRequest request, int limit) {
        super(request, limit);
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public ServletInputStream getInputStream() throws IOException {
        if (!hasRead.get()) {
            hasRead.set(true);
            return super.getInputStream();
        }
        return new ServletInputStreamWrapper(super.getContentAsByteArray());
    }

    static class ServletInputStreamWrapper extends ServletInputStream {

        private final InputStream is;

        private boolean finished = false;

        public ServletInputStreamWrapper(byte[] bytes) {
            this.is = new ByteArrayInputStream(bytes);
        }

        @Override
        public int read() throws IOException {
            int data = this.is.read();
            if (data == -1) {
                this.finished = true;
            }
            return data;
        }

        @Override
        public int available() throws IOException {
            return this.is.available();
        }

        @Override
        public void close() throws IOException {
            super.close();
            this.is.close();
        }

        @Override
        public boolean isFinished() {
            return this.finished;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException();
        }
    }
}
