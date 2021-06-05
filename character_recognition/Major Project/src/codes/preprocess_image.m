function [preprocessed_image] = preprocess_image(image_dir,image_name)
    % This function performs Image Preprocessing
    % Image Preprocessing include BINARIZATION, UNIVERSE OF DISCOURSE and IMAGE RESIZING
    % Note : For BINARIZATION the image must be in grayscale format
    try
            [image_data,image_map] = imread(strcat(image_dir,image_name));
       %% Check the type of image and apply corresponding binarization technique 
            info = imfinfo(image_name);
            str  = info.ColorType;
            if strcmp(str,'indexed')
                binary_image = imbinarize(ind2gray(image_data,image_map));
            elseif strcmp(str,'grayscale')
                if islogical(image_data)
                    binary_image = image_data;
                else
                    binary_image =imbinarize(image_data);
                end
            else
                binary_image = imbinarize(rgb2gray(image_data));
            end
            %imshow(binary_image);
       %% Maintaining background (shadow) as black
            rows         = size(binary_image,1);
            columns      = size(binary_image,2);
            white_pixels = sum(binary_image(:));
            black_pixels = (rows*columns) - white_pixels;
            if white_pixels > black_pixels
                binary_image = imcomplement(binary_image);
            else
               fprintf('Preprocess ');disp(image_name);
            end
            %imshow(binary_image);
       %% Selecting Universe of discourse and resizing image to 60x40 standard format
            discoursed_image   = discourser(binary_image);
            preprocessed_image = imresize(discoursed_image,[60 40]);
            %imwrite(preprocessed_image,strcat('..\preprocessed_images\',image_name));
    catch e
        disp(image_name);
        throw(e);
    end
end