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

## Double Parity DFA
![](/Visuals/DoubleParityDFA.png)
A bit more complex situation occures when we decide to build an automata that will check for both the parity of 1's and parity of 0's at the same time, given a binary string.Our number of total states grows exponentialy by a factor of two. Which does make sense when you think about it.  

**This is what a translation of this program would sound in automata world:**
* **Four states** -> **even-even parity** (accepted state),**even-odd parity** (not accepted state),**odd-even parity** (not accepted state), & **odd-odd parity** (not accepted state)
* **Only binary input** -> any combination of 1's and 0's
* Transition work exactly like the ones in the previous example, with first parity defining the parity of zeros. So, even-odd state defines even number of 0's and odd number of 1's. Being in that state and given an input of 1, we would transition to even-even state. Same logic applies to all states.
* **Start state** will be the **accepted state** (even-even parity)
* A **set of accepting states** is a set containing **only one state (even-even parity state)**

### Classes
We use exactly the same classes as the previous example. Only there are more states and transition definitions this time.


## Regular Expression Automaton
Regular expressions are very interesting topic to dive into and explore. They provide a variety of applications if used properly and in a smart way. In this section, I will briefly explain how regular expressions work and what sorts of problems you might solve using them.  
A regular expression, also called **regex**, is usually thought of as a sequence of characters in a string or a text. The concept arose in the 1950s when the American mathematician Stephen Cole Kleene formalized the description of a regular language. Regular expressions are meant to search for patterns, and therefore they have to be implemented to perform some sort of procedural scan of the entire input text.  
* In this project, I made an automaton that is able to find a given combination of characters in a bigger input string. Given a set of characters, this automaton will create exactly as much states as there are characters plus one extra state for the start state. Each state represents a separate character.  
Each state also defines the transition to the next state, for a "correct" input.  
* To explain a bit better, if we are looking to create an automaton that will search for a pattern **"ABC", we need to create three states, state A,state B and state C. The start state represents any state that is not defined in our pattern.**  
* Now we have to **define mappings (transitions) between our states**. Start state has only two possible transitions, first one is a transition to the first state of the pattern (in this case state A), which occurrs give the input A, and a transition to itself for any other input.  
* State A (first state of the pattern) will have transition to State B, when input B occurs and we are already in State A. It will also have transition to the start State for any other input.  
* All other states are mapped in a similar manner, only they also have to contain a transition to the first State of the pattern, in case we enter the lookup process and start anoter one midway through.  
* Only the last state in the automaton will be accepted!

## Prerequisites 

```
Standard Java Libraries
```

## Authors

* **Jurica Kenda** - *Initial work* - [JuricaKenda](https://github.com/juricaKenda)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
