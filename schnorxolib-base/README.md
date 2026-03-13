# Notes on the Module "Base"

This module is a small helper library with a couple of auxiliary and -- well -- base classes. 

## Major Changes 
### V. 0.1.1 &rarr; V. 0.2
Took on the `FixedPointNumber` issue (cf. section "Known Issues" below):

* Introduced `BigFraction` from 
  [Apache Commons Numbers](https://commons.apache.org/proper/commons-numbers): 
  Now, all fraction-related stuff -- esp. parsing a fraction-string -- is delegated to it.
* Added methods to both accept and return a `BigFraction`. However, internally, the value is still represented as `BigDecimal` (as currently, too many things depend on it).
* Improved test coverage of `FixedPointNumber`.

*Rationale*:

`FixedPointNumber` has several issues, the most obvious of them being:

* It does not represent a fixed point number (its name is misleading).
* It does not represent what it *should* represent either (remember: all numbers in Gnucash and KMyMoney are represented as fractions internally, and all computations are exact. So why, the hell, should we operate with this `BigDecimal`-wrapper when, in fact, a `BigFraction`-wrapper would, by its very nature, better suit our needs?)
* Its string parser has logic implemented the rationale and use of which is not evident. Even worse: It parses both decimal-point and fraction representations -- in the *same* method, without explicit choice, but by some internal magic and heuristics.

I guess that Marcus (the original author), being short of time and having a deadline to meet, just copy-pasted it from one of his previous projects, where he had to implement some very specific requirements (that don't really make sense in the context of `JGnuCashLib(NTools)`) and quickly hacked some additional GnuCash-specific stuff into it. I also guess that he was fully aware of the result being a sub-optimal solution, and that he wanted to take care of this some day when he would have some time blabla things developed differently than originally envisaged blabla real-life issues demanded his attention blabla he never had the time blabla eventually lost interest. We all know how this is (so, no offence, Marcus).

In short, I want to get rid of this class in the long run. And given the fact that `FixedPointNumber` is an imporant and basic low-level class used hundreds of times throughout the `JGnuCashLibNTools` and `JKMyMoneyLibNTools` projects, used in many interface methods, I cannot just write a drop-in-replacement in 10 minutes, search-replace it everywhere and hope that still everything will work. Instead, I will take the slowly-young-man-hold-your-horses-just-take-one-step-after-the-other-approach over several releases.

This version is the first step towards this goal.

Note: I also have thought about essentially making `FixedPointNumber` a wrapper for `BigFraction`, leveraging on information hiding, and carried
out some experiments on that. I came to the conclusion that just deprecating it after a while is probably the better solution (I have not
made my final decision on this yet).

### V. 0.1 &rarr; V. 0.1.1
Some minor corrections / improvements.

### V. 0.1
Newly created.

## Planned
* Class `FixedPointNumber`: 
  * Support for more data types in constructors and operation methods.
  * Possibly change internal representation to BigRational 
    (cf. known issues below).

## Known Issues
* Overall: Very ugly (but useful).

* Class `FixedPointNumber`: I essentially took it from the original author. Apart from the fact that it has a misleading name, I am less than convinced that it is optimal for 
[`JGnuCashLib`](https://github.com/jross765/JGnuCashLibNTools)
and 
[`JKMyMoneyLib`](https://github.com/jross765/JKMyMoneyLibNTools), 
at least in its current form: 

	* Using it gets clumsy from time to time.
	* It is not immutable (for historical reasons, as opposed to what one would expect from a `number`-based class).
	* Parsing strings is not safe and not locale-flexible.
	* *Last not least*: It is not compatible with GnuCash's and KMyMoney's internal way of doing exact computations with rational numbers. 

  ==> After re-evaluation, the current maintainer decided to a) introduce BigFraction (done), b) deprecate `FixedPointNumber` (to do), c) eventually get rid of it (to do). For details, cf. notes on V. 0.2.
