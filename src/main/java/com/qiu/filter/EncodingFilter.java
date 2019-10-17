package com.qiu.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
        protected FilterConfig config;
        protected String encoding = null;

        @Override
        public void init(FilterConfig arg0) throws ServletException {
            this.config = arg0;

            /* 从web.xml里读取编码的配置初始值 */
            this.encoding = config.getInitParameter("Encoding");
        }
        /**具体实现的过滤方法*/
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain arg2)
                throws IOException, ServletException {
            if (encoding == null) {
                encoding = "UTF-8";
            }
            //基本上这三种编码设置后,请求\响应都不会出现乱码了
            response.setCharacterEncoding(encoding);
            request.setCharacterEncoding(encoding);
            response.setContentType("text/html;charset=" + encoding);
            arg2.doFilter(request, response);
        }

        /**自动调用destroy方法*/
        @Override
        public void destroy() {
            encoding = null;
        }



}
