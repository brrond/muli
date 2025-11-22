GIT=git
GRADLE=./gradlew # each project decides its own gradle via gradlew

.PHONY: install-dev
install-dev: install-submodules install-muli-classpath

.PHONY: install-muli-classpath
install-muli-classpath:
	cd muli-classpath && $(GRADLE) install

.PHONY: install-muli-lang
install-muli-lang: install-muli-classpath

.PHONY: install-root-submodules
install-root-submodules:
	$(GIT) submodule update --init

.PHONY: install-submodules
install-submodules: install-root-submodules

muli-env.zip: install-root-submodules install-muli-classpath
	cd muli-env && $(GRADLE) distZip 
	cp "$$(ls -t ./muli-env/muli-runtime/build/distributions/muli-runtime-* | head -1)" muli-env.zip

muli-lang.jar: install-root-submodules install-muli-lang
	cd muli-lang && $(GRADLE) jar
	cp "$$(ls -t ./muli-lang/muli-lang-*.jar | head -1)" muli-lang.jar

.PHONY: dist
dist: muli-env.zip muli-lang.jar muli-env-st.zip
