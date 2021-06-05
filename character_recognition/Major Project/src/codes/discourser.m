function [discoursed_image] = discourser(image)
    % This function selects universe of discourse of the image
    % EXPLANATION :-
    %   It works by finding uppermost, lowermost, leftmost and rightmost skeleton of the image.
    %       -> The uppermost skeleton is found by traversing from first row to last row  
    %          in image matrix until atleast one non-zero element is found in corresponding row.
    %       -> The lowermost skeleton is found by traversing from last row to first row
    %          in image matrix until atleast one non-zero element is found in corresponding row.
    %       -> The leftmost skeleton is found by traversing from first column to last column
    %          in image matrix until atleast one non-zero element is found in corresponding column.
    %       -> The rightmost skeleton is found by traversing from last column to first column
    %          in image matrix until atleast one non-zero element is found in corresponding column.
    %   Note : k = find(X,n) returns the first n indices corresponding to the nonzero elements in X.
    %          Example 1: a = [ 0 0 0 1 1 0 1];
    %                  -> find(a) gives [4,5,7] as output i.e,column numbers
    %                  -> find(a,2) gives [4,5] as output
    %                  -> find(a,1) gives [4] as output
    %          Example 2: a = [0 0 0]
    %                  -> find(a) gives [] as output i.e, 0x0 empty array
    %% VARIABLES DECLARATION AND INITIALIZATION
        currentimage = image;
        row          = size(currentimage,1); % Represents image height
        column       = size(currentimage,2); % Represents image width
        uppermost    = 1;
        lowermost    = row;
        leftmost     = 1;
        rightmost    = column;
    %% FINDING UPPERMOST IMAGE SKELETON ROW VALUE 
        for i = 1:row
            currentrow = currentimage(i,:);
            if isempty(find(currentrow,1))
                continue;
            else
                uppermost = i;
                break;
            end
        end
    %% FINDING LOWERMOST IMAGE SKELETON ROW VALUE
        for i = row:-1:1
            currentrow = currentimage(i,:);
            if isempty(find(currentrow,1))
                continue;
            else
                lowermost = i;
                break;
            end
        end
    %% FINDING LEFTMOST IMAGE SKELETON COLUMN VALUE
        for i = 1:column
            currentcolumn = currentimage(:,i);
            if isempty(find(currentcolumn,1))
                continue;
            else
                leftmost = i;
                break;
            end
        end
    %% FINDING RIGHTMOST IMAGE SKELETON COLUMN VALUE
        for i = column:-1:1
            currentcolumn = currentimage(:,i);
            if isempty(find(currentcolumn,1))
                continue;
            else
                rightmost = i;
                break;
            end
        end
    %% CROPPING THE IMAGE TO FIT THE ENTIRE CHARACTER SKELETON
        discoursed_image = currentimage(uppermost:lowermost,leftmost:rightmost);
end