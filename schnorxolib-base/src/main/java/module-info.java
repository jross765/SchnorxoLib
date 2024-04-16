module schnorxoborx.schnorxolib {
	requires static java.sql;
	requires java.desktop;
	requires static org.slf4j;
	requires static commons.configuration;

	exports xyz.schnorxoborx.base.basetypes;
	exports xyz.schnorxoborx.base.beanbase;
	exports xyz.schnorxoborx.base.cmdlinetools;
	exports xyz.schnorxoborx.base.dateutils;
	exports xyz.schnorxoborx.base.numbers;
}
