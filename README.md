# ScoreBoard

#### Asumptions
- Particular team can be present only once in the board at the same time.
- Much more reads from board than writes.
- Value of the score may be also decreased (ex due to mistake compensation)
- Value of the score can be set to any value, not only increased by one. (But cannot be negative)
- Team names are case-sensitive.
- class ScoreBoard is kind of an aggregate root. The state of it cannot be modified just by classes like Match or Team outside the ScoreBoard, only through ScoreBoard behaviour.



#### Some thoughts and ideas  (not introduced for sake of simplification):
- Order of score parameters is relevant, first is home score, then away score, it may lead to some mistakes. It may be improved.
- Id of Match is auto incremented and is used to sort order for second parameter. In case of saving in real DB may lead to some issues, can be improved.
- for classes like ScoreBoardValidator and MatchRepository, interfaces can be added. Then they can be injected in constructor by the interface to ScoreBoard. It can give possibility to use other implementations (save to real DB, other sort behaviour, also introduce DI).
- Instead of getting summary method, ScoreBoardListener class can be provided. Then client classes can register for ScoreBoard updates.
