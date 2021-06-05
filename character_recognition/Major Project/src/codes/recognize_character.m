function [] = recognize_character(Trained_data)
    % This function recognizes the input character image

    % VISUALIZATION AND EXPLANATION OF CHI_SQUARE TEST
            
        %   |************************************************************************* OBSERVED MATRIX **************************************************************************|
        %   !------------------------!!--------------------------------!!--------------------------------!   !--------------------------------!!---------------------------------!
        %   | trained_feature_vector ||            o(r1,c1)            ||            o(r1,c2)            |...|            o(r1,cn)            ||             row1_sum            |
        %   |------------------------||--------------------------------||--------------------------------|   |--------------------------------||-------------------------------- |
        %   | input_feature_vector   ||            o(r2,c1)            ||            o(r2,c2)            |...|            o(r2,cn)            ||             row2_sum            |
        %   |------------------------||--------------------------------||--------------------------------|   |--------------------------------||---------------------------------|
        %   | Total                  || o(r3,c1) = o(r1,c1) + o(r2,c1) || o(r3,c2) = o(r1,c2) + o(r2,c2) |...| o(r3,cn) = o(r1,cn) + o(r2,cn) || total_sum = row1_sum + row2_sum |
        %   !------------------------!!--------------------------------!!--------------------------------!   !--------------------------------!!---------------------------------!
            
        %   Find row1_proportion = row1_sum / total_sum
        %   Find row2_proportion = row2_sum / total_sum
            
        %   |************************************************************** EXPECTED MATRIX **************************************************************|
        %   !------------------------!!------------------------------------!!------------------------------------!   !------------------------------------!
        %   | trained_feature_vector || e(r1,c1)= o(r3,c1)*row1_proportion || e(r1,c2)= o(r3,c2)*row1_proportion |...| e(r1,cn)= o(r3,cn)*row1_proportion |
        %   |------------------------||------------------------------------||------------------------------------|   |------------------------------------|
        %   | input_feature_vector   || e(r2,c1)= o(r3,c1)*row2_proportion || e(r2,c2)= o(r3,c2)*row2_proportion |...| e(r2,cn)= o(r3,cn)*row2_proportion |
        %   !------------------------!!------------------------------------!!------------------------------------!   !------------------------------------!

        %   Find chi_square_value = (([o(ri,cj)-e(ri,cj)]^2) / e(ri,cj)) [ i = 1 to 2 ; j = 1 to n ]
        %   Repeat the above steps for all the trained images and store the chi_square_value in an array.
        %   The output is the character label whose chi_square_value is minimum.
	
    % NOTE : character_array contains character labels in the order of images trained
    try
        %% VARIABLES DECLARATION AND INITIALIZATION
            character_array         = Trained_data.character_array;
            trained_features_matrix = Trained_data.trained_features_matrix;
            image_names             = Trained_data.image_names;
            no_of_feature_vectors = size(trained_features_matrix,1);
            no_of_characters = length(character_array);
            % If character_array has no elements OR number of trained vectors is NOT EQUAL to number of characters then exit  
            if((isempty(character_array))||(no_of_feature_vectors ~= no_of_characters))
                return;
            end
        %% TAKING INPUT FROM USER
            while true                                                      % Repeats until the user inputs an image which is to be recognized
                [filename,user_cancelled] = imgetfile();
                if ~user_cancelled
                    [image_dir,image_name,extension]=fileparts(filename);
                    image_dir = strcat(image_dir,'/');
                    input_image = strcat(image_name,extension);
                    input_preprocessed_image = preprocess_image(image_dir,input_image); % Preprocessing the input image
                    break;
                end
            end
        %% EXTRACTING SHADOW AND EXTENDED SHADOW FEATURE VECTORS OF THE INPUT IMAGE
            [input_missing_features_image,input_image_shadow_vector] = extract_shadow_features(input_preprocessed_image);
            input_image_extended_shadow_vector = extract_extended_shadow_features(input_missing_features_image);
            input_image_feature_vector         = [input_image_shadow_vector,input_image_extended_shadow_vector];
            chi_square_values                  = zeros(1,no_of_feature_vectors); % Initializing to store chi_square_values
            wbar = waitbar(0,'Recognizing the image...','Name','Recognizing...');
            wbar.CloseRequestFcn = '';
            pos                             = get(wbar,'Position');         % Get position of waitbar
            set(wbar,'Position',pos+[0 0 0 30]);                            % Changing height of waitbar
        %% RECOGNIZING THE INPUT IMAGE BY PERFORMING CHI-SQUARE TEST BETWEEN THE INPUT IMAGE AND ALL THE TRAINED IMAGES
            disp('Recognizing the image...');
            steps = no_of_feature_vectors;
            start_time = clock;
            for i = 1 : no_of_feature_vectors
                row1_sum = 0;
                row2_sum = 0;
                current_feature_vector   = trained_features_matrix(i,:);
                length_of_current_vector = length(current_feature_vector);
                observed_matrix = zeros(3,length_of_current_vector+1);
                expected_matrix = zeros(2,length_of_current_vector);
                row1_proportion = 0;
                row2_proportion = 0;
                chi_square_sum  = 0;
                
                for j = 1:length_of_current_vector
                  % Construct contingency table for each trained image vector combined with input image vector
                    observed_matrix(1,j) = current_feature_vector(j);
                    observed_matrix(2,j) = input_image_feature_vector(j);
                    observed_matrix(3,j) = observed_matrix(1,j) + observed_matrix(2,j);
                    row1_sum = row1_sum + observed_matrix(1,j);
                    row2_sum = row2_sum + observed_matrix(2,j);
                end
                
                total_sum = row1_sum + row2_sum;
                observed_matrix(1,length_of_current_vector+1) = row1_sum;
                observed_matrix(2,length_of_current_vector+1) = row2_sum;
                observed_matrix(3,length_of_current_vector+1) = total_sum;
                if(total_sum > 0)
                    row1_proportion = row1_sum / total_sum;
                    row2_proportion = row2_sum / total_sum;
                end
                
                for j = 1:length_of_current_vector
                  % Finding expected values from the observed values and calculating chi-square value of the data
                    expected_matrix(1,j) = row1_proportion * observed_matrix(3,j);
                    expected_matrix(2,j) = row2_proportion * observed_matrix(3,j);
                    
                    for k = 1:2
                        fe = expected_matrix(k,j);
                        if(fe > 0)
                            fo = observed_matrix(k,j);
                            chi_square_sum = chi_square_sum + ((fo-fe)^2/fe);
                        end
                    end
                    
                end
                chi_square_values(i) = chi_square_sum;
             %%  Calculating execution time for recognizing the input image and displaying status in progress bar
                %[ BEGIN ]
                elapsed_time = etime(clock,start_time);
                %disp(elapsed_time);
                progress_percentage = i/steps;
                total_time = (1.0/progress_percentage)*elapsed_time;
                remaining_time = total_time - elapsed_time;
                minutes = remaining_time / 60;
                seconds = rem(remaining_time,60);
                status_message = {  sprintf('Performing chi-square test');...
                                    sprintf('%.f %% completed',100*progress_percentage);...
                                    sprintf('Time remaining : %.f m : %02.f s',minutes,seconds)...
                                 };
                waitbar(i/steps,wbar,status_message);
                %[END]
            end
        %% DETERMINING CHARACTER LABEL AND DISPLAYING OUTPUT
            [~,index] = min(chi_square_values);       % To neglect minimum value '~' is used
            delete(wbar);
            predicted_image = strcat('../training_dataset/',char(image_names(index)));
            predicted_character = character_array(index);
            output(predicted_image,predicted_character);
    catch e
        disp(getReport(e));
        CreateStruct.Interpreter = 'tex';         % Used to modify text size of msgbox
        CreateStruct.WindowStyle = 'modal';       % Disables a user to interact with other MATLAB windows before responding to the dialog box
        h = msgbox('\fontsize{10} Unable to recognize the image. Try again...!','Error','error',CreateStruct);
        waitfor(h);
        delete(wbar);
        disp('Failed to recognize the image due to... [<strong> FAILURE </strong>]');
        fprintf(2,'Exception in recognize_character()\n%s\n',e.message);
    end
end