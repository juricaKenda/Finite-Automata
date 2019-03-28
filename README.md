 # Finite State Automata

* This repository puts the focus on finite state automata or finite state machines\par
* The idea behind these subprojects is to truly understand the functioning of final state machines and get a peak under the hood of how more complex systems and programs (that use this kind of model) work
* This repository is divided into more subfolders, each containing its own topic or automata that works in a particular manner
* You will find detailed descriptions (including the visuals) in this README file (if you scroll low enough to find what you came for)
![](/Visuals/FSA.png)
## Brief background on Finite Automata
A finite automaton has a set of states and its control which moves it from state to state in response to external inputs .  One of the crucial distinctions among classes of finite automata is whether that control is deterministic (meaning that the automaton cannot be in more than one state at any time)  or  nondeterministic ( meaning that it may be in several states at once/ NOT that we don't know the state it is going to go to). Most of the automata soultions and programs provided in this repository will be DFA or Deterministic Finite Automata.
In a nutshell, you now know a very loose definition of the Finite State Machine.
## Deterministic Finite Automata
This subclass of finite automata is the one where each state combined with an external input, responds with exactly one output and that output is the next state of the automata.
Personally, I like the light switch example to represent a DFA. If the current state is ON and you give it a certain input PRESS (in this case the only input), the DFA changes its state to OFF state.     
**All DFAs consist of :**   
* A finite set of states (in the code denoted allStates)  
* A finite set of input symbols (in the code sometimes manually checked, sometimes denoted allowedInputs)  
* A transition function that will assing a next state to the current state, when a particular input arrives (in the code class is denoted TransitionFunction and a variable denoted delta)  
* A start state   
* A set of accepting states   
## Parity DFA
![](/Visuals/ParityDFA.png)
I believe it is important to start with a simple,direct (sometimes hardcoded) model and then to gradually expand the idea into a more general purpose program. That is why I decided to start with the implementation of a DFA that **checks for parity (of number one) in a given binary string.**  


**This is what a translation of this program would sound in automata world:**
* **Two states** -> **even parity** (accepted state) & **odd parity** (not accepted state)
* **Only binary input** -> any combination of 1's and 0's
* Assuming we start in the state that is the one of even parity, for **each input of '1'** we go to the **odd parity state**, from which we can **only get back** to the accepted state with **another input of 1**. For any **input zero**, we just **stay in the current state**, although those transitions should also be defined
* **Start state** will be the **accepted state** (even parity)
* A **set of accepting states** is a set containing **only one state (even parity state)**

### Classes
* **State class** - represents any state uniquely identified with a String 'name'; also referenced as a 'token' of that state when performing transitions (token = id = name)
* **Transition Function class** - represents an entitiy that will be performing the transitions as inputs come along; it contains a HashMap that should have defined key-value pairs for any key(input+current state token) -> value (next state)


## Prerequisites 

```
Standard Java Libraries
```

## Authors

* **Jurica Kenda** - *Initial work* - [JuricaKenda](https://github.com/juricaKenda)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
