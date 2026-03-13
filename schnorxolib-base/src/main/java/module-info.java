module schnorxoborx.schnorxolib {
	
	requires static java.sql;
	requires java.desktop;
	requires static org.slf4j;
	
	// ----------------------------
	
	requires transitive commons.configuration;
	requires transitive org.apache.commons.cli;
	requires transitive org.apache.commons.numbers.core;
	requires transitive org.apache.commons.numbers.fraction;

	// ----------------------------
	
	exports xyz.schnorxoborx.base.basetypes;
	exports xyz.schnorxoborx.base.beanbase;
	exports xyz.schnorxoborx.base.cmdlinetools;
	exports xyz.schnorxoborx.base.dateutils;
	exports xyz.schnorxoborx.base.numbers;
}
