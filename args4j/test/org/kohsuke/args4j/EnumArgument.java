package org.kohsuke.args4j;

class EnumArgument {
	@Argument(required = true, metaVar="ANIMAL")
	EnumAttribute.Animal myAnimal;
}