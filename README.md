This is the source code and binaries distribution of the Masking Distance Tool,

you need to set the environment variable 'FAULTY' with:

export FAULTY = path to jar files

HOW TO COMPILE:

ant compile jar

HOW TO RUN IT:

Usage: ./faulty-mask [options] [nominal model path] [faulty model path]

Output: lim n->infinity of 1/1+n, where n is the number of faults masked

Options: 
-d : create dot file 
-t : print error trace 
-s : start simulation 
-l : treat deadlock as error state too
 

Some examples can be found
in the folder: tests/

This is the first version of the tool, we expect to add further features and examples
soon.

