# Notes on the Module "Base"

This module is a small helper library with a couple of auxiliary and -- well -- base classes. 

## Major Changes 
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
	* Parsing strings is not safe and not locale-flexible.
	* It is not compatible with GnuCash's and KMyMoney's internal way of doing exact computations with rational numbers. 

  ==> Re-evaluate: Should we possibly switch completely to another class, possibly based on `BigRational` (cf. test cases) or s.t. comparable?
