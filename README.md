# Todarch ![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)

## Code of Conduct

This project is managed by the [Todarch Code of Conduct](CODE_OF_CONDUCT.md).
By participating, you are expected to uphold this code.

## Android Studio IDE setup

Todarch requires Android Studio version 3.2 or higher.

Todarch uses [ktlint](https://ktlint.github.io/) to check Kotlin coding styles.
- First, close Android Studio if it's open
- Download ktlint by following instructions at [ktlint README](https://github.com/shyiko/ktlint/blob/master/README.md#installation)
- Inside the project root directory run:

  `ktlint --apply-to-idea-project --android`

- Remove ktlint if desired:

  `rm ktlint`

- Start Android Studio


## Versioning

Todarch uses [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/todarch/todarch-android/tags). 

## Authors

* **Melih Gültekin** - *Initial work* - [melomg](https://github.com/melomg)

See also the list of [contributors](https://github.com/todarch/todarch-android/graphs/contributors) who participated in this project.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.
