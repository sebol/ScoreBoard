# ScoreBoard


#### Asumptions
- Particular team can be present only once int the board at the same time.
- Much more reads from board than writes.



#### Some things may be a little smelly, but because of simple implementation may be enough good:
- Matches have not any ids, they can be found only by String (name of the team)
- Order of score parameters is relevant, first is home score, then away score, it may lead to some mistakes.