function [] = output(predicted_image,predicted_character)
    %   This function displays the predicted character inside a message box
    
    %% DISPLAYING THE OUTPUT IN A CUSTOM MESSAGE BOX
    try
        %% VARIABLES DECLARATION AND INITIALIZATION
            CreateStruct.Interpreter = 'tex';         % Used to modify text size of msgbox
            CreateStruct.WindowStyle = 'modal';       % Disables a user to interact with other MATLAB windows before responding to the dialog box
            [image_data,image_map] = imread(predicted_image);
            info = imfinfo(predicted_image);
            str  = info.ColorType;
        %% CONVERTING PREDICTED IMAGE INTO INDEXED FORMAT TYPE AND SCALING TWICE THE IMAGE SIZE
            if strcmp(str,'indexed')
                % DO NOTHING
            elseif strcmp(str,'grayscale')
                if isempty(image_map)                                           % Without COLORMAP
                    [image_data,image_map] = gray2ind(image_data);
                else                                                            % With COLORMAP
                    [image_data,image_map] = gray2ind(image_data,image_map);
                end
            else
                [image_data,image_map] = rgb2ind(image_data,image_map);
            end
            [image_data,image_map] = imresize(image_data,image_map,2);
        %% DISPLAYING THE OUTPUT
            output_message = strcat('\fontsize{12} ',sprintf('Predicted as : %s',predicted_character));
            msgbox(output_message,'Output','custom',image_data,image_map,CreateStruct);
            disp('Image recognition completed successfully [<strong> SUCCESS </strong>]');
            %disp(predicted_image);
            disp('Predicted as :');
            disp(predicted_character);
    catch e
        h = msgbox('\fontsize{12} Failed to output the character','Error','error',CreateStruct);
        waitfor(h);
        disp('Image recognition is unsuccessful ! [<strong> UNSUCCESS </strong>]');
        disp('Failed to output the character due to... [<strong> FAILURE </strong>]');
        fprintf(2,'Exception in output()\n %s \n',e.message);
    end
end

