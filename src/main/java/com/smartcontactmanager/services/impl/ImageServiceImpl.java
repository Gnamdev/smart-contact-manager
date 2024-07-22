package com.smartcontactmanager.services.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.smartcontactmanager.Helper.AppConstants;

@Service
public class ImageServiceImpl implements com.smartcontactmanager.services.ImageService {

    private Cloudinary cloudinary;

    public ImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile contactImage, String filename) {

        // code likhnaa hia jo image ko upload kar rha ho

        // String filename = UUID.randomUUID().toString();

        try {
            byte[] data = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);

            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                    "public_id", filename));

            // Upload image to Cloudinary

            // Log the response
            // System.out.println("Cloudinary Response: " + response);

            // // Check if response is valid JSON
            // if (response == null || !(response instanceof Map)) {
            // throw new RuntimeException("Invalid JSON response from server: " + response);
            // }

            return this.getUrlFromPublicId(filename);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error time of uploading image");
            return null;
        }

        // and return raha hoga : url

    }

    @Override
    public String getUrlFromPublicId(String publicId) {

        return cloudinary
                .url()
                .transformation(
                        new Transformation<>()
                                .width(AppConstants.CONTACT_IMAGE_WIDTH)
                                .height(AppConstants.CONTACT_IMAGE_HEIGHT)
                                .crop(AppConstants.CONTACT_IMAGE_CROP))
                .generate(publicId);

    }

}