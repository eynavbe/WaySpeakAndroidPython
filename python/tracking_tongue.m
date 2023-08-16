function output = tracking_tongue(arg1)
    %a = system('python C:\Users\einav\Downloads\final_final_project\final_final_project\Tongue2.py');
    opticFlow = opticalFlowFarneback;
    countLeft = 0;
    countRight = 0;
    countStraight = 0;
    countNose = 0;
    countChin = 0;

    %Read the content of the text file.
    fileID = fopen('points.txt', 'r');
    data_str = fscanf(fileID, '%c');
    fclose(fileID);
    %fprintf("data 0: %s \n", data_str) k po
    %Extract the data into a cell array of matrices.
    data_str = strrep(data_str, '[', ''); % Remove square brackets
    %fprintf("data 1: %s \n", data_str)
    data_str = strrep(data_str, ']', '');
    %fprintf("data 2: %s \n", data_str)
    data_str = strrep(data_str, '),', '');
    data_str = strrep(data_str, ')', '');
    %fprintf("data 3: %s \n", data_str)
    data_str = strsplit(data_str, ' ('); % Split the data into individual strings
    fprintf("Frames number: %d \n", numel(data_str)/32)
    data_str{1} =  data_str{1}(2:end);
    
    % Initialize a cell array to store the matrices.
    data = cell(numel(data_str)/32, 32);
    
    % Convert each string to a matrix and store it in the cell array.
    for i = 1:numel(data_str)/32 %num of frame
        for j = 1:32 %num of points
            data{i}{j} = data_str{(i-1)*32 + j};
            fprintf("data{i}{j}: %s \n", data{i}{j})
                    values = str2double(strsplit(data_str{(i-1)*32 + j}, ', ')); % Convert to numeric values
     % Check if the first value is 5
            if values(1) == 48
                x1 = values(2)-15; % Extract the second value
               % y11 = values(3); % Extract the third value
            end
             if values(1) == 49
                 x4 = values(2); % Extract the second value
             end
            if values(1) == 50
                y1 = values(3); % Extract the third value
                y3 = values(3);
                x3 = values(2)-10;
                 
                y4 = values(3); % Extract the third value
                width1 =  values(2) - (x1+15)+20;
                
                height1 = values(3); % Extract the third value
            end
            if values(1) == 54
               % x22 = values(2)-40; % Extract the second value
               % y22 = values(3); % Extract the third value
            end
             if values(1) == 52
                x2 = values(2); 
                y2 = values(3); 
                width3 = x2 - x3+10;
             end
           %  if values(1) == 61
             %   x4 = values(2); % Extract the second value
             %   y4 = values(3); % Extract the third value
           % end
            
            if values(1) == 58
                height1 = (values(3) - height1)* 2; % Extract the third value
                y4 = y4 - height1/2 +20;
            end
    
            if values(1) == 67
                x5 = values(2)-10; 
                y5 = values(3)+15; 
            end
        end
    end
    
    % Print the extracted data (optional).
    %disp(data);
    fprintf("num after matrix: %s \n", data{1}{1})
    fprintf("num after matrix end: %s \n", data{numel(data_str)/32}{32})
    
    
    % Load the video
    videoFile = 'C:\Users\einav\Downloads\final_final_project\final_final_project\output.mp4';  % Replace with the path to your folder
    videoReader = VideoReader(videoFile);
    
    % Create a video player for visualization
    videoPlayer = vision.VideoPlayer();
    %x2 = 280;
    %y2 = 317;
    width2 = width1;
    height2 = height1;
    %x1 = 180;
    %y1 = 318;
    %210, 318)
    k = 1;
    %width1 = 50;
    % Loop through video frames
    while hasFrame(videoReader)
        for j = 1:32 %num of points
            data{k}{j} = data_str{(k-1)*32 + j};
            fprintf("data{i}{j}: %s \n", data{k}{j})
                    values = str2double(strsplit(data_str{(k-1)*32 + j}, ', ')); % Convert to numeric values
     % Check if the first value is 5
            if values(1) == 48
                x1 = values(2)-15; % Extract the second value
               % y11 = values(3); % Extract the third value
            end
             if values(1) == 49
                 x4 = values(2); % Extract the second value
             end
            if values(1) == 50
                y1 = values(3); % Extract the third value
                y3 = values(3);
                x3 = values(2)-10;
                 
                y4 = values(3); % Extract the third value
                width1 =  values(2) - (x1+15)+20;
                
                height1 = values(3); % Extract the third value
            end
            if values(1) == 54
               % x22 = values(2)-40; % Extract the second value
               % y22 = values(3); % Extract the third value
            end
             if values(1) == 52
                x2 = values(2); 
                y2 = values(3); 
                width3 = x2 - x3+10;
             end
           %  if values(1) == 61
             %   x4 = values(2); % Extract the second value
             %   y4 = values(3); % Extract the third value
           % end
            
            if values(1) == 58
                height1 = (values(3) - height1)* 2; % Extract the third value
                y4 = y4 - height1/2 +20;
            end
    
            if values(1) == 67
                x5 = values(2)-10; 
                y5 = values(3)+15; 
            end
        end
        frame = readFrame(videoReader);
        
        % Convert the frame to grayscale for simplicity
        grayFrame = rgb2gray(frame);
        
        % Apply edge detection (you might need to adjust parameters)
        edgeFrame = edge(grayFrame, 'Canny');
        
        % Specify regions of interest for each tongue movement
        % You might need to adjust these coordinates based on your video resolution
        regionMoveLeft = [x1, y1, width1, height1];  % Define coordinates
        regionMoveRight = [x2, y2, width2, height2]; % Define coordinates
        regionMoveStraight = [x3, y3, width3, height2]; % Define coordinates
        regionMoveNose = [x4, y4, width2, height2]; % Define coordinates
        regionMoveChin = [x5, y5, width2, height2]; % Define coordinates
    
        % Define more regions for other movements
        
        % Extract regions from the edge-detected frame
        regionEdgeMoveLeft = edgeFrame(y1:y1+height1, x1:x1+width1);
        regionEdgeMoveRight = edgeFrame(y2:y2+height2, x2:x2+width2);
        regionEdgeMoveStraight = edgeFrame(y3:y3+height2, x3:x3+width3);
        regionEdgeMoveNose = edgeFrame(y4:y4+height2, x4:x4+width2);
        regionEdgeMoveChin = edgeFrame(y5:y5+height2, x5:x5+width2);
    
        % Extract more regions for other movements
    
        % Calculate the percentage of edge pixels in each region
        percentEdgeMoveLeft = sum(regionEdgeMoveLeft(:)) / numel(regionEdgeMoveLeft);
        percentEdgeMoveRight = sum(regionEdgeMoveRight(:)) / numel(regionEdgeMoveRight);
        percentEdgeMoveStraight = sum(regionEdgeMoveStraight(:)) / numel(regionEdgeMoveStraight);
        percentEdgeMoveNose = sum(regionEdgeMoveNose(:)) / numel(regionEdgeMoveNose);
        percentEdgeMoveChin = sum(regionEdgeMoveChin(:)) / numel(regionEdgeMoveChin);
    
    
        % Calculate more percentages for other movements
        %  fprintf('percentEdgeMoveRight: %d \n', percentEdgeMoveRight);
    
        % Check if each movement was carried out based on thresholds
        thresholdMoveLeft = 0.115; % Adjust the threshold as needed
        thresholdMoveRight = 0.115; % Adjust the threshold as needed
        thresholdMoveStraight = 0.13;
        thresholdMoveNose = 0.115;
        thresholdMoveChin = 0.115;
    
    
        
        % Set more thresholds for other movements
        
        % Detect and display results
        if percentEdgeMoveLeft > thresholdMoveLeft
            countLeft = countLeft + 1;
            
            fprintf('Move the tongue to the Left \n');
            disp('Move the tongue to the Left');
        end
        if percentEdgeMoveRight > thresholdMoveRight
            countRight = countRight + 1;
            fprintf('Move the tongue to the right \n');
            disp('Move the tongue to the right');
    
        end
        if percentEdgeMoveStraight > thresholdMoveStraight
                countStraight = countStraight + 1;

            fprintf('Move the tongue to the Straight \n');
            disp('Move the tongue to the Straight');
            
        end
        if percentEdgeMoveNose > thresholdMoveNose
                countNose = countNose + 1;

            fprintf('Move the tongue to the Nose \n');
            disp('Move the tongue to the Nose');
            
        end
        if percentEdgeMoveChin > thresholdMoveChin
                countChin = countChin + 1;

            fprintf('Move the tongue to the Chin \n');
            disp('Move the tongue to the Chin');
            
        end
        
        % Add more if conditions for other movements
        
        % Display the frame with detected regions
        combinedFrame = frame;
       %  combinedFrame(y1:y1+height1, x1:x1+width1, :) = repmat(regionEdgeMoveLeft, [1, 1, 3]) * 255;
        % combinedFrame(y2:y2+height2, x2:x2+width2, :) = repmat(regionEdgeMoveRight, [1, 1, 3]) * 255;
        % combinedFrame(y3:y3+height2, x3:x3+width3, :) = repmat(regionEdgeMoveStraight, [1, 1, 3]) * 255;
             combinedFrame(y4:y4+height2, x4:x4+width2, :) = repmat(regionEdgeMoveNose, [1, 1, 3]) * 255;
            %combinedFrame(y5:y5+height2, x5:x5+width2, :) = repmat(regionEdgeMoveChin, [1, 1, 3]) * 255;
    
    
        % Combine more regions for other movements
        step(videoPlayer, combinedFrame);
    end
    % if arg1 == 3
    output = [countLeft, countRight, countStraight, countNose, countChin];
    close all

end











