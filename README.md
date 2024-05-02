# Project SchnorxoLib

`SchnorxoLib` is a small free and open-source Java library that contains a couple of auxiliary classes (a part of them exceptions) used in the following projects:

* [JGnuCash Lib 'n' Tools](https://github.com/jross765/jgnucashlibntools)

* [JKMyMoney Lib 'n' Tools](https://github.com/jross765/jkmymoneylibntools)

Its sole purpose is to reduce trivial redundancies between these projects.

## FAQ

**Was it really necessary to carve out a new lib, just for these few classes? That is yet one more dependency for the two above-mentioned projects to manage.**

Well, the author has asked himself that question, and this fact is the primary reason why he has been hesistating for so long before finally taking this step. At the beginning, there were just 2 or 3 small redundant files to manage, so he did not bother to carve them out. But it became more with each month of development. Finally, with the introduction of the "tools" modules, he became convinced that this was the moment to introduce this new lib, because things would have become unmanageable.

**What about that funny name and pseudo-domain? Where does that come from?**

The name means nothing. The author just chose it in lack of a better one. He could just as well have chosen "Hullabaloo", "Snoggowobble", "Snowwhite", "Henry the Great" or whatever. In order to avoid name clashes and misunderstandings, he chose one for which the search engine yielded no results. About the domain: No, it is not registered (to his knowledge, at least), and there is no organization behind it.

**This collection of classes seems rather uneven, artificial and thrown-together. Don't you think so?**

It is, you're right. The author does not expect to win a "most-beautiful-and-well-designed-lib-of-the-year" contest with that. 

Parts of it were taken from a not-much-better lib that he uses internally. He cannot / will not publish this lib as it is for various reasons which he won't dive into here. That internal lib of his uses package names with a real domain name that he won't publish here as well -- also for specific reasons that are out of scope here. This is the main reason for that funny name.

**Is this going to be re-iterated over? Can we expect that to be a full-blown, beautifully-designed and MECE base library everybody can use one day?**

Very probably not. The author will probably add some additional auxiliary classes over the next months, he might possibly even open a new module (although that seems a stretch right now), but that's about as far as it ever will go.

**Are the two above-mentioned projects the only ones that use this lib?**

Currently, they are the only *public* ones, yes. The author uses the lib in internal projects of his as well. Will he, one day, publish one or two of these? Possibly.

## Modules and Further Details
Currently, there is just one module:

* [Base](https://github.com/jross765/schnorxolib/tree/master/schnorxolib-base/README.md)

## Major Changes
./.

## Compiling the Sources
To compile the sources, do the following:

1) Make sure that you have Maven installed on your system.

2) Clone the repository

3) Check out the latest version tag. In this case: `V_0_1`.

   For reasons the author cannot understand, there are plenty of self-appointed super-pro developers who do not seem to understand the concept of version tags and configuration management, i.e. that it's *not* always the latest version that you have to take...

4) Compile the sources and install the resulting JAR file in your local repository by typing:

    `$ ./build.sh`

     Then, either copy the resulting file to whereever you want to have it, 
     or do it the standard Maven way be typing:

    `$ mvn install`
