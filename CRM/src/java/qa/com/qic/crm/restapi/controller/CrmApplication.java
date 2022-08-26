/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qa.com.qic.crm.restapi.controller;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.core.exceptions.IOExceptionMapper;
import org.glassfish.jersey.server.provider.BasicAuthProvider;

import qa.com.qic.crm.restapi.resources.CrmIntegration;
import qa.com.qic.crm.restapi.resources.CrmOperation;
import qa.com.qic.crm.restapi.resources.WhatsAppIntegration;
import qa.com.qic.crm.restapi.uam.CompanyIdentifier;

/**
 *
 * @author thoufeak.rahman, ravindar.singh
 */
@ApplicationPath("/api")
public class CrmApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(CrmOperation.class);
        resources.add(CrmIntegration.class);
        resources.add(WhatsAppIntegration.class);
        resources.add(MultiPartFeature.class);
        resources.add(CompanyIdentifier.class);
        resources.add(BasicAuthProvider.class);
        resources.add(IOExceptionMapper.class);
        return resources;
    }

}
