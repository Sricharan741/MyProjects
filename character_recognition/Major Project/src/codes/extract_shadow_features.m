function [missing_features_image,shadow_feature_vector] = extract_shadow_features(preprocessed_image)
    % This function extracts shadow features from all the four directions of the image
    
    % It is done by storing the count of visited black pixels of background until it reaches either 
    % one white pixel or to the end of that row/column based on the direction of extraction.
    
    % NOTE : During the process of counting this function complements the visited
    %        black pixels into white pixels in order to know the missing features.
    %% EXTRACTING SHADOW FEATURES FROM THE CHARACTER IMAGE
    try
        %% VARIABLES DECLARATION AND INITALIZATION
            row                 = size(preprocessed_image,1); % Represents image height
            column              = size(preprocessed_image,2); % Represents image width
            missing_features_image = preprocessed_image; % Used to store the missing shadow features
            east                = zeros(1,row);
            west                = zeros(1,row);
            north               = zeros(1,column);
            south               = zeros(1,column);
        %% Shadow Features from WEST DIRECTION
            for r = 1:row                                                           % EXAMPLE: Assume r=1, row=10, column=5
                currentrow = preprocessed_image(r,:);                               %           currentrow = [0 0 1 1 0]
                leftmost_white_pixel_index = find(currentrow,1);                    %           leftmost_white_pixel_index = 3
                if isempty(leftmost_white_pixel_index)                              %           condition fails
                    black_pixels_visited = row;                                     %               won't execute
                    missing_features_image(r,:) = 1;                                %               won't execute
                elseif leftmost_white_pixel_index == 1                              %           condition fails
                    black_pixels_visited = 0;                                       %               won't execute
                else                                                                %           condition pass
                    black_pixels_visited = leftmost_white_pixel_index - 1;          %               black_pixels_visited = 3-1 = 2
                    missing_features_image(r,1:leftmost_white_pixel_index-1) = 1;   %             missing_features_images(1,1:2) = 1
                end
                west(r) = black_pixels_visited;                                     %           west(1) = 2
            end
        %% Shadow Features from EAST DIRECTION
            for r = 1:row                                                           % EXAMPLE: Assume r=1, row=10, column=5
                currentrow = preprocessed_image(r,:);                               %           currentrow = [0 1 0 1 1]
                rightmost_white_pixel_index  = find(currentrow,1,'last');           %           rightmost_white_pixel_index = 5
                if isempty(rightmost_white_pixel_index)                             %           condition fails
                    black_pixels_visited = row;                                     %               won't execute
                    missing_features_image(r,:) = 1;                                %               won't execute
                elseif rightmost_white_pixel_index == column                        %           condition pass
                    black_pixels_visited = 0;                                       %               black_pixels_visited = 0;
                else                                                                %           condition fails
                    black_pixels_visited = column - rightmost_white_pixel_index;    %               won't execute
                    missing_features_image(r,rightmost_white_pixel_index+1:end) = 1;%            won't execute
                end
                east(r) = black_pixels_visited;                                     %           east(1) = 0
            end
        %% Shadow Features from NORTH DIRECTION
            for c = 1:column                                                        % EXAMPLE: Assume c=1, row=10, column=5
                currentcolumn = preprocessed_image(:,c);                            %           currentcolumn = [1 0 1 0 1 1 0 1 0 0]
                uppermost_white_pixel_index = find(currentcolumn,1);                %           uppermost_white_pixel_index = 1;
                if isempty(uppermost_white_pixel_index)                             %           condition fails
                    black_pixels_visited  = column;                                 %               won't execute
                    missing_features_image(:,c) = 1;                                %               won't execute
                elseif uppermost_white_pixel_index == 1                             %           condition pass
                    black_pixels_visited = 0;                                       %               black_pixels_visited = 0
                else                                                                %           condition fails
                    black_pixels_visited = uppermost_white_pixel_index - 1;         %               won't execute
                    missing_features_image(1:uppermost_white_pixel_index-1,c) = 1;  %               won't execute
                end
                north(c) = black_pixels_visited;                                    %           north(1) = 0
            end
        %% Shadow Features from SOUTH DIRECTION
            for c = 1:column                                                        % EXAMPLE: Assume c=1, row=10, column=5
                currentcolumn = preprocessed_image(:,c);                            %           currentcolumn = [0 0 0 0 0 0 0 0 0 0]
                lowermost_white_pixel_index  = find(currentcolumn,1,'last');        %           lowermost_white_pixel_index = []
                if isempty(lowermost_white_pixel_index)                             %           condition pass
                    black_pixels_visited  = column;                                 %               black_pixels_visited = 10
                    missing_features_image(:,c) = 1;                                %               missing_features_image(:,1) = 1
                elseif lowermost_white_pixel_index == row                           %           condition fails
                    black_pixels_visited = 0;                                       %               won't execute
                else                                                                %           condition fails
                    black_pixels_visited = row - lowermost_white_pixel_index;       %               won't execute
                    missing_features_image(lowermost_white_pixel_index+1:end,c) = 1;%               won't execute
                end
                south(c) = black_pixels_visited;                                    %           south(1) = 10
            end
        %% Combining north, east, south and west arrays to form shadow feature vector
            shadow_feature_vector = [north,east,south,west];
    catch e
        throw(e);
    end
end