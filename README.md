This is the source code and binaries distribution of the Masking Distance Tool,

you need to set the environment variable 'MASKD' with:

export MASKD = [path to jar files]

HOW TO COMPILE:

ant compile jar

HOW TO RUN IT:

Usage: ./maskD [options] [nominal model path] [faulty model path]

Output: lim n->infinity of 1/(1+sumMaskedFaults[0..n]), where n is the number of steps and sumMaskedFaults[inf..sup] is the amount of faults masked from inf to sup

Options: 
-d : create dot file 
-t : print error trace 
-s : start simulation 
-l : treat deadlock as error state too
 

Some examples can be found
in the folder: tests/

More especifically, on tests/nominal/ are the nominal models, and on tests/faulty/ are them faulty counterparts.

For the case of phils, run with option -l, for example: ./maskD -l ../tests/nominal/phils/phils3.test ../tests/faulty/phils/phils3.test

This is the first version of the tool, we expect to add further features and examples
soon.

