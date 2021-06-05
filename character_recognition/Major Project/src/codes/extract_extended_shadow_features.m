function [extended_feature_vector] = extract_extended_shadow_features(missing_features_image)
    % This function extracts extended shadow features from all the four directions of the image
    
    % This function basically extracts the missing features which are known as "extended shadow features"
    % by simply counting the black pixels in each row/column based on the direction of extraction.
    
    % NOTE : In the extraction process, the features from EAST direction and the WEST direction will be the same.
    %                               and the features from NORTH direction and the SOUTH direction will be the same.
    
    % INFO : The sum(X) function calculates sum of array X and here it is used to find the count of white_pixels in X.
    %% EXTRACTING EXTENDED SHADOW FEATURES FROM THE CHARACTER IMAGE
    try
        %% VARIABLES DECLARATION AND INITIALIZATION
            row     = size(missing_features_image,1);           % Represents image height
            column  = size(missing_features_image,2);           % Represents image width
            east    = zeros(1,row);
            north   = zeros(1,column);
        %% Extended Shadow Features from EAST DIRECTION
            for r =1:row                                        % EXAMPLE : Assume r=1,row=10,column=5
                currentrow = missing_features_image(r,:);       %            currentrow = [0 1 0 1 1]
                black_pixels_count = column - sum(currentrow);  %            black_pixels_count = 5 - 3 = 2
                east(r) = black_pixels_count;                   %            east(1) = 2
            end
        %% Extended Shadow Features from WEST DIRECTION
            west = east;                                        % Since EAST and WEST features are same
        %% Extended Shadow Features from NORTH DIRECTION
            for c =1:column                                     % EXAMPLE : Assume c=1,row=10,column=5
                currentcolumn = missing_features_image(:,c);    %            currentcolumn = [0 1 1 0 1 0 1 1 0 0]
                black_pixels_count = row - sum(currentcolumn);  %            black_pixels_count = 10 - 5 = 5
                north(c) = black_pixels_count;                  %            north(1) = 5
            end
        %% Extended Shadow Features from SOUTH DIRECTION
            south = north;                                      % Since NORTH and SOUTH features are same
        %% Combining north, east, south and west arrays to form extended shadow feature vector
            extended_feature_vector = [north,east,south,west];
    catch e
        throw(e);
    end
end