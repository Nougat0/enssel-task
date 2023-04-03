//package com.enssel.verbena.zuul.filter;
//
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
//
//@Component
//@Orderho
//public class PathRewriteFilter extends ZuulFilter {
//
//	@Override
//	public boolean shouldFilter() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public Object run() throws ZuulException {
//		RequestContext context = RequestContext.getCurrentContext();
//		String requestUri = context.getRequest().getRequestURI();
//		System.out.println("❤❤"+requestUri);
//        if (requestUri.startsWith("/ui") && !requestUri.startsWith("/ui/")) {
//            // Rewrite the request path to include a trailing slash
//            context.setRequest(new CustomHttpServletRequestWrapper(context.getRequest(), requestUri + "/"));
//        }		
//		return null;
//	}
//
//	@Override
//	public String filterType() {
//		// TODO Auto-generated method stub
//		return "pre";
//	}
//
//	@Override
//	public int filterOrder() {
//		// TODO Auto-generated method stub
//		return 1;
//	}
//
//}
