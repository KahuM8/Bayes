This was compiled with the ecs machines but should run with anything later than java 17

you can check your java version with the command: java --version

you can recompile manualy with the comands javac src/bayes/*.java
cfm Bayes.jar manifest.txt

if this says if you get a class not found exception, i have found that the easiest way to fix this is with the following instructions:

if you have not got that version of java you can recompile it in vscode with your version of java.
you can do this by looking in the explorer column
go to java projects. if you hover next to where it says java projects click export to jar
it is a symbol that looks like this   |->
at the top of the screen where the search bar is there should be a drop down that says Bayes and <Without main class>
click Bayes


then run the bat file or sh file again







To run program on windows:
double click the run.bat file

To run on the ecs machines or linux
open terminal at this location, and use the command
bash run.sh