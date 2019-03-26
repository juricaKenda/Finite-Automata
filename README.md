{\rtf1\ansi\ansicpg1252\deff0\nouicompat\deflang2057{\fonttbl{\f0\fnil\fcharset0 Calibri;}}
{\*\generator Riched20 6.3.9600}\viewkind4\uc1 
\pard\sa200\sl276\slmult1\f0\fs22\lang9 # Finite State Automata\par
\par
* This repository puts the focus on finite state automata or finite state machines\par
* The idea behind these subprojects is to truly understand the functioning of final state machines and get a peak under the hood of how more complex systems and programs (that use this kind of model) work\par
* This repository is divided into more subfolders, each containing its own topic or automata that works in a particular manner\par
* You will find detailed descriptions (including the visuals) in this README file (if you scroll low enough to find what you came for)\par
![]()\par
## Brief background on Finite Automata\par
A finite automaton has a set of states and its control which moves it from state to state in response to external inputs .  One of the crucial distinctions among classes of finite automata is whether that control is deterministic (meaning that the automaton cannot be in more than one state at any time)  or  nondeterministic ( meaning that it may be in several states at once/ NOT that we don't know the state it is going to go to). Most of the automata soultions and programs provided in this repository will be DFA or Deterministic Finite Automata.\par
In a nutshell, you now know a very loose definition of the Finite State Machine.\par
## Deterministic Finite Automata\par
This subclass of finite automata is the one where each state combined with an external input, responds with exactly one output and that output is the next state of the automata.\par
Personally, I like the light switch example to represent a DFA. If the current state is ON and you give it a certain input PRESS (in this case the only input), the DFA changes its state to OFF state. \par
All DFAs consist of :  \par
*A finite set of states (in the code denoted allStates)\par
*A finite set of input symbols (in the code sometimes manually checked, sometimes denoted allowedInputs)\par
*A transition function that will assing a next state to the current state, when a particular input arrives (in the code class is denoted TransitionFunction and a variable denoted delta)\par
*A start state \par
*A set of accepting states \par
//TODO individual project readmes\par
## Prerequisites \par
\par
```\par
Standard Java Libraries\par
```\par
\par
## Authors\par
\par
* **Jurica Kenda** - *Initial work* - [JuricaKenda](https://github.com/juricaKenda)\par
\par
## License\par
\par
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details\par
}
 