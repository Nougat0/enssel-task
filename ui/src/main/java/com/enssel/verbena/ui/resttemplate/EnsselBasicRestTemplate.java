//package com.enssel.verbena.ui.resttemplate;
//
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.DefaultUriBuilderFactory;
//import org.springframework.web.util.UriComponentsBuilder;
//
//public class EnsselBasicRestTemplate extends RestTemplate implements EnsselRestTemplate{
//    //🔔 인자생성자 🔔
//	public EnsselBasicRestTemplate(String resourceURI) {
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(resourceURI);
//        this.setUriTemplateHandler(new DefaultUriBuilderFactory(builder));
//    }
//}
