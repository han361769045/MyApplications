package com.luleo.myapplications.rest;


import com.luleo.myapplications.model.BaseModel;

import org.androidannotations.rest.spring.annotations.Accept;
import org.androidannotations.rest.spring.annotations.Delete;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.RequiresAuthentication;
import org.androidannotations.rest.spring.annotations.RequiresCookie;
import org.androidannotations.rest.spring.annotations.RequiresHeader;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.annotations.SetsCookie;
import org.androidannotations.rest.spring.api.MediaType;
import org.androidannotations.rest.spring.api.RestClientErrorHandling;
import org.androidannotations.rest.spring.api.RestClientHeaders;
import org.androidannotations.rest.spring.api.RestClientRootUrl;
import org.androidannotations.rest.spring.api.RestClientSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;

/** SimpleXmlHttpMessageConverter.class,
 * Created by leo on 2015/7/21.
 */

@Rest(rootUrl = "http://192.168.1.111",
        requestFactory = MyRequestFactory.class,
        interceptors = { MyInterceptor.class },
        converters = {StringHttpMessageConverter.class,	MappingJackson2HttpMessageConverter.class,
                  FormHttpMessageConverter.class,ByteArrayHttpMessageConverter.class,GsonHttpMessageConverter.class })
public interface MyRestClient extends RestClientRootUrl, RestClientSupport, RestClientHeaders, RestClientErrorHandling {

    @Post("/Post/{id}")
    @RequiresHeader(value = {"Content-Type", "Content-Disposition", "filename", "charset"})
    String uploadAvatar(int id, MultiValueMap<String, Object> formData);


    @Delete("/deleteTest/{id}")
    @RequiresAuthentication()
    BaseModel deleteTest(int id);


    @Get("/Login/{id}")
    @SetsCookie("JSESSIONID")
    BaseModel login(int id);

    /**
     * 必须传入一个JSESSIONID
     * 也就是说，必须在登录的情况下才可以
     *
     * @param page
     * @param rows
     * @return
     */
    @Post("/getVideoInfoList/{page}/{rows}")
    @RequiresCookie("JSESSIONID")
    String getVideoInfoList(int page, int rows);

    @Put("/Put")
    String putTest(BaseModel bm);



    @Get("")
    ResponseEntity getTest();

    //void setHeader(String value,String va);

    @Post("/Image/{id}")
    String uploadImage(int id, MultiValueMap<String, Object> data);


    @Get("")
    @Accept(value = MediaType.APPLICATION_XML)
    String testXML();

    @Get("")
    @Accept(value = MediaType.APPLICATION_OCTET_STREAM)
    String testXMLFile();

}

