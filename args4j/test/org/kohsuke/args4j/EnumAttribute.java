package org.kohsuke.args4j;

public class EnumAttribute {
	
	enum Animal { HORSE, DUCK }
	
	@Option(name="-animal", usage="Give your favorite animal.")
    Animal myAnimal;
	
}
