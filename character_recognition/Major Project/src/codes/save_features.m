function [] = save_features(feature_vectors_matrix,trained_features_file_path)
    %   This function saves the feature vectors of all the trained images into a text file
    
    %   [ HOW? :The dlmwrite method writes the data inside feature_vectors_matrix into the
    %   textfile specified in the filepath with the delimiter specified ]
    try
        disp('Saving the trained features...');
        dlmwrite(trained_features_file_path,feature_vectors_matrix,'delimiter',' ');
        disp('Trained features saved successfully [<strong> SUCCESS </strong>]');
        disp(strcat('Check features at <strong>"',which(trained_features_file_path),'"</strong>'));
    catch exception
        disp('Failed to save trained features due to... [<strong> FAILURE </strong>]'); 
        fprintf(2,'Exception in save_features()\n%s\n',exception.message);
    end
end

