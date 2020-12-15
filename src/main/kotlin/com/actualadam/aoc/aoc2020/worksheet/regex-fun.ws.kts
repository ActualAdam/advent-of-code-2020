val text = """dark olive bags contain 2 muted brown bags, 1 mirrored tomato bag, 4 bright black bags.
    | faded coral bags contain 3 drab cyan bags, 1 light aqua bag.""".trimMargin()

//((\d+ \w+ \w+) bag(s, |\.))+

val bagModifierPattern = "(\\w+ \\w+) bags contain ((\\d+ (\\w+ \\w+) bag((s)*(, |.)))+)".toRegex()

val result = bagModifierPattern.findAll(text).toList()[0]
    result.value

result.groupValues[2].split(",", ".").dropLastWhile { it.isBlank() }
