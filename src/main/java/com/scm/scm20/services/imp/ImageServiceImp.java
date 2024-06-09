package com.scm.scm20.services.imp;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.scm20.helper.AppConstants;
import com.scm.scm20.services.ImageService;

@Service
public class ImageServiceImp implements ImageService {

    private Cloudinary cloudinary;

    public ImageServiceImp(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile contactImage) {
        // code likhna h jo code ko upload kr rha hoga server pr

        String filename = UUID.randomUUID().toString();

        try {
            byte[] data = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap("public_id", filename));
            return this.getUrlPublicId(filename);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getUrlPublicId(String publicId) {
        return cloudinary
                .url()
                .transformation(new Transformation<>()
                        .width(AppConstants.CONTACT_IMAGE_WIDTH)
                        .height(AppConstants.CONTACT_IMAGE_HIEGHT)
                        .crop(AppConstants.CONTACT_IMAGE_CROP))
                .generate(publicId);
    }

}
