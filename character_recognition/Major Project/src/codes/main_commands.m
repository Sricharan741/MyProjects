% main_commands script calls the train_images() and recognize_character() functions

% EXTENDED SHADOW FEATURE EXTRACTION TECHNIQUE FOR CHARACTER RECOGNITION

% Note : To run this either check that the current folder is set to 'codes' or click run and click addtopath

cd(fileparts(which(mfilename))); % Useful to change the current folder as 'codes' after run is clicked

addpath(genpath('..\..\..\Major Project')); % Useful to generate paths for the entire subfolders of Major Project and add them into the search path

clc;	% Useful to clear command window

%load('dataset_trained_feature_vectors.mat')  	% Uncommment this statement inorder to load already trained data

%Trained_data = train_images('../training_dataset/','trained_feature_vectors.txt'); 	% Uncomment this inorder to train the images freshly 

recognize_character(Trained_data); % Uncomment this to recognize image after getting the trained data.