function [Trained_data] = train_images(dataset_path,trained_features_file)
    % This function trains the image dataset
    
    % It is done by getting the image, extracting shadow features , extended shadow features and combining both to form a
    % final feature vector which is used for character recognition.
    
    % NOTE : This function stores the features of image whose vector array have atleast one non-zero element.
    try
        %% VARIABLES DECLARATION AND INTIALIZATION
            Images                   = dir(strcat(dataset_path,'Image *.*'));% Stores the images into a structure variable
            no_of_images             = length(Images);                       % calculate number of images
            processed_images         = 0;
            image_names              = string(zeros(1,no_of_images));        % Used to store image names for further usage
            character_array          = blanks(no_of_images);                 % Initializing character array for storing charactets
            trained_features_matrix  = zeros(no_of_images,400);              % Initializing to store features of all images in dataset
            if no_of_images == 0
                disp('Oops! Either no images are present to train in given directory OR the directory path is invalid');
                disp('Please check and try again!');
                Trained_data.character_array = char.empty;
                Trained_data.trained_features_matrix = double.empty;
                Trained_data.image_names = string.empty;
                return;
            end
            trained_features_file_path      = strcat('..\trained_feature_vectors\',trained_features_file);
            index                           = 0;
         %% CREATING A WAIT BAR WITH CANCEL BUTTON
            wbar                            = waitbar(0,'Training the character images...','Name','Training...','CreateCancelBtn','setappdata(gcbf,''canceling'',1)');
            steps                           = no_of_images;                 % Represents total steps to fill progress bar
            pos                             = get(wbar,'Position');         % Get position of waitbar
            set(wbar,'Position',pos+[0 0 0 30]);                            % Changing height of waitbar
            setappdata(wbar,'canceling',0);                                 % Disables close option in waitbar
        %% TRAINING THE DATASET IMAGES
            disp('Training the images...');
            start_time = clock;  % get current data and time
            for i = 1:no_of_images
                if getappdata(wbar,'canceling')                             % Check for clicked Cancel button
                    break;
                end
             %% PROCESSING THE IMAGE THROUGH FEATURE EXTRACTION
                image_name              = Images(i).name;
                preprocessed_image      = preprocess_image(dataset_path,image_name);     % Preprocessing the image
                [missing_features_image,shadow_feature_vector] = extract_shadow_features(preprocessed_image); % Extracting shadow features from the image
                extended_shadow_feature_vector = extract_extended_shadow_features(missing_features_image); % Extracting extended shadow features from the image
                final_feature_vector = [shadow_feature_vector,extended_shadow_feature_vector]; % Storing the final features of the image
                if any(final_feature_vector(:))                             % Checking if any array element is non-zero (Read NOTE)
                    index = index+1;
                    trained_features_matrix(index,:) = final_feature_vector;
                    character_array(index) = image_name(7);                 % Store character label of the image
                    image_names(index) = string(image_name);
                end
              % Calculating execution time for training the images and displaying progress bar
                %[ BEGIN ]
                elapsed_time = etime(clock,start_time);
                progress_percentage = i/steps;
                total_time = (1.0/progress_percentage)*elapsed_time;
                remaining_time = total_time - elapsed_time;
                minutes = remaining_time / 60;
                seconds = rem(remaining_time,60);
                status_message = {  sprintf('%.f %% completed',100*progress_percentage);...
                                    sprintf('Time remaining : %.f m : %02.f s',minutes,seconds);...
                                    sprintf('Currently training : ''%s'' ',image_name)...
                                 };
                waitbar(i/steps,wbar,status_message);
             % [ END ]
                processed_images = processed_images + 1;
            end
        %% SAVING IF ALL THE IMAGES ARE TRAINED 
            if processed_images == no_of_images
               disp('Training completed successfully [<strong> SUCCESS </strong>]');
               character_array = character_array(1:index);                  % Removing all the empty character elements
               trained_features_matrix = trained_features_matrix(1:index,:);% Removing all the rows which doesn't have any data elements
               image_names = image_names(1:index);
               waitbar(1,wbar,'Saving the trained features...');
               save_features(trained_features_matrix,trained_features_file_path);% Saving the trained features
            else
                character_array = char.empty;
                trained_features_matrix = double.empty;
                image_names = string.empty;
                delete(wbar);
                disp('Oops! Training cancelled !');
                disp('Training was unsuccessful ! [<strong> UNSUCCESS </strong>]');
            end
          % Storing in a structure array
            Trained_data.character_array = character_array;
            Trained_data.trained_features_matrix = trained_features_matrix;
            Trained_data.image_names = image_names;
            delete(wbar);
    catch e
        character_array = char.empty;                                       % returns an empty 0 x 0 character array
        trained_features_matrix = double.empty;                             % returns an empty 0 x 0 double array
        image_names = string.empty;                                         % returns an empty 0 x 0 string array
      % Storing in a structure array
        Trained_data.character_array = character_array;
        Trained_data.trained_features_matrix = trained_features_matrix;
        Trained_data.image_names = image_names;
        delete(wbar);
        h = msgbox({'Training Failed !';'Please check the code and try again.'},'Error','error');
        waitfor(h); %  blocks statements from executing until the specified object closes (is deleted)
        disp('Failed to train the images due to... [<strong> FAILURE </strong>]');
        fprintf(2,'%s\n',getReport(e));
    end
end