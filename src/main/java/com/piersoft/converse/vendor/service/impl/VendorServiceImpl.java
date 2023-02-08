package com.piersoft.converse.vendor.service.impl;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.piersoft.converse.vendor.mapper.VendorRequestDOMapper;
import com.piersoft.converse.vendor.persistence.model.Vendor;
import com.piersoft.converse.vendor.persistence.repository.VendorRepository;
import com.piersoft.converse.vendor.request.dto.VendorOnboardRequestBody;
import com.piersoft.converse.vendor.service.VendorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class VendorServiceImpl implements VendorService {

    private Logger logger = LoggerFactory.getLogger(VendorServiceImpl.class);

    @Autowired
    private VendorRequestDOMapper vendorRequestDOMapper;

    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public String onboardVendor(VendorOnboardRequestBody vendorOnboardRequestBody) {
        Vendor vendor = vendorRequestDOMapper.requestBodyToDO(vendorOnboardRequestBody);
        vendorRepository.save(vendor);
        return vendor.getId();
    }

    @Override
    public void updateCompanyDetails(VendorOnboardRequestBody vendorOnboardRequestBody) {
        String docId = vendorOnboardRequestBody.getDocId();
        if(StringUtils.isBlank(docId)){
            logger.error("Invalid document id, document id cannot be null or empty");
            throw new ResourceNotFoundException("Invalid document id, document id cannot be null or empty");
        }
        Optional<Vendor> vendorOptional  = vendorRepository.findById(docId);
        if(vendorOptional.isEmpty()){
            logger.error("No document exists with id:{}", docId);
            throw new ResourceNotFoundException("No document exists with id:"+docId);
        }
        Vendor vendor = vendorOptional.get();
        Vendor requestVendor = vendorRequestDOMapper.requestBodyToDO(vendorOnboardRequestBody);
        vendor.setCompanyDetailsDO(requestVendor.getCompanyDetailsDO());
        vendorRepository.save(vendor);
    }

    @Override
    public void updateCompanyContactInfo(VendorOnboardRequestBody vendorOnboardRequestBody) {
        String docId = vendorOnboardRequestBody.getDocId();
        if(StringUtils.isBlank(docId)){
            logger.error("Invalid document id, document id cannot be null or empty");
            throw new ResourceNotFoundException("Invalid document id, document id cannot be null or empty");
        }
        Optional<Vendor> vendorOptional  = vendorRepository.findById(docId);
        if(vendorOptional.isEmpty()){
            logger.error("No document exists with id:{}", docId);
            throw new ResourceNotFoundException("No document exists with id:"+docId);
        }
        Vendor vendor = vendorOptional.get();
        Vendor requestVendor = vendorRequestDOMapper.requestBodyToDO(vendorOnboardRequestBody);
        vendor.setContactInfoDO(requestVendor.getContactInfoDO());
        vendorRepository.save(vendor);
    }

    @Override
    public void updateCompanyKYC(VendorOnboardRequestBody vendorOnboardRequestBody) {
        String docId = vendorOnboardRequestBody.getDocId();
        if(StringUtils.isBlank(docId)){
            logger.error("Invalid document id, document id cannot be null or empty");
            throw new ResourceNotFoundException("Invalid document id, document id cannot be null or empty");
        }
        Optional<Vendor> vendorOptional  = vendorRepository.findById(docId);
        if(vendorOptional.isEmpty()){
            logger.error("No document exists with id:{}", docId);
            throw new ResourceNotFoundException("No document exists with id:"+docId);
        }
        Vendor vendor = vendorOptional.get();
        Vendor requestVendor = vendorRequestDOMapper.requestBodyToDO(vendorOnboardRequestBody);
        vendor.setKycDO(requestVendor.getKycDO());
        vendorRepository.save(vendor);
    }
}
