//////Configurations Needed////////

1. Create any folder. eg: "Submission"
2. Place the Jar file inside that.
3. create an input folder named "input" inside the "Submission" folder.
4. Place all the sample input files in the input folder

Folder Structure would be like

<Submission>
|
|___<input folder>(Make Sure Name should be input only)
|    |
|    |____Sample1.txt
|    |
|    |____Sample2.txt
|    |
|    |____Sample3.txt	
|
|___GMAT.jar


////// How to run the Jar file ////////

1. Navigate to location of the JAR file.
2. type the command 

 java -jar GMAT.jar

////////Result/////////

After running the command any of the following result might come.

Case 1. "Process finished successfully without any errors, Please check the ouput.txt file"
Case 2. "Process finished successfully with some errors, Please check the log.txt and ouput.txt file"
Case 3. "Process finished successfully without errors, Please check the log.txt file"

For Case 1. Out put is generated Successfully And You can check the file named Output.txt to check the solutions.
For Case 2. Out put is generated Successfully for some files and could not generate for few .txt files 
            Check the file named output.txt for solutions.
            Check the file named logFile.txt for errors.

For Case 3. Output file could not be gneerate.
           Check the file named logFile.txt for errors.


//////////////////Assumptions Made ////////////////////////

1. Only .txt file will be read.
   If any other file is kept in the folder then it will not be read and Ignored. 
2. Allowed pattern in the file is 
      Either of them will be permissible else the Out put will not be proper.

      <Q1|HARD|Tag1>          <q1|HARD|Tag1>
      <Q1|EASY|Tag2>    or    <q2|EASY|Tag2>
      <Q1|HARD|Tag3>          <q1|HARD|Tag3> 
   
    Duplicate Question Id allowed in the file but only the first occurence will be considered
    in the above case Q1|HARD|Tag1 or q1|HARD|Tag1 will be considered.    

3. File can have empty lines in between.
4. If the pattern mentioned in the point 2 is not followed then the file will not be read and the output will not be generated.













