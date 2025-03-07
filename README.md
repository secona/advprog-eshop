# advprog-eshop

<details>
<summary>
  :one: Module 1
</summary>

## Reflection 1
In developing this project, several clean code principles were used to improve readability and maintainability. Meaningful names were used for variables and functions to ensure clarity in their purpose. For instance, I used the variable name `productService` instead of a generic name like `service` to avoid ambiguity.

Comments were used sparingly and only when necessary, ensuring that the code remains self-explanatory without excessive annotation. This approach aligns with best practices, where well-structured and readable code reduces the need for additional comments.

Secure coding practices are also applied by the use of `java.util.Optional<T>` to minimize the risk of NullPointerExceptions. This helps in writing safer, more resilient code by avoiding null-related errors.

While these principles where adhered to, there is still room for improvement. Logging practices have yet to be implemented, which could significantly improve debugging and monitoring. Implementing structured logging with log levels (INFO, DEBUG, ERROR) would enhance debugging, troubleshooting, and security monitoring. Additionally, implementing rate limiting would further protect brute-force attacks.

## Reflection 2
After writing the unit tests, I feel more confident that my code is working as intended. If I change the implementation, I can quickly run the tests again to see if any of the changes I made results in an error. This immediate feedback loop ensures that unintended side effects are caught early, reducing debugging time. Additionally, it is much faster to verify if a method is correct since I no longer need to manually test the application's functionality.

As for how many unit tests should be implemented, I believe they should cover all possibilities, including edge cases and failure scenarios. In many cases, edge cases and failure scenarios can be just as common as successful scenarios, making them crucial to test. However, even if code coverage reaches 100%, it does not guarantee that the application is bug-free. Code coverage only indicates which lines of code have been executed. It does not guarantee the correctness of the logic. For example, failure to handle case-insensitive strings, such as `YES` and `yes` as arguments, can result in 100% code coverage but incorrect logic.

To improve maintainability, I would structure unit tests using a base test class. By inheriting from the base class in every test suite, shared setup logic can be centralized. This approach minimizes redundancy, as any changes to the setup function would only need to be made once in the base class rather than in every individual test suite. However, it is important to ensure that the base class does not introduce unnecessary dependencies, as that could reduce test flexibility and make debugging more difficult.
</details>

<details>
<summary>
  :two: Module 2
</summary>
  
## Fixed Code Quality Issues

### Pinned-Dependencies issues
The root cause of this issue is the versions specified in CI scripts and Dockerfile sources. The dependencies need to be pinned to exact versions rather than general ones. For example, specifying `v4` is not acceptable, whereas using a commit hash like `9e8d0789d4a0fa9ceb6b1738f7e269594bdd67f0` is preferred. This requirement is in place to mitigate supply chain attacks.

To resolve this, I allow the CI scripts and Dockerfile to run, then record the exact versions they use and replace the general version references accordingly. For CI scripts, the relevant version information is typically found in the `Set up Job` step, as seen in this example: https://github.com/secona/advprog-eshop/actions/runs/13356226014/job/37299382344.

### License issue
I simply add a `LICENSE.md` file in the root of the project. I chose GPLv3, because why not :).

### Token-Permissions Issues
The root cause of this issue is CI scripts having more than needed permissions to run. This can be a problem when the CI scripts are compromised and it has all access to the repository. Therefore, to resolve this, I explicitly add permissions to the CI scripts on what they have access to.

### Java Source Code Issues
For these issues, I simply follow the provided instructions. If the issue is an unused import, I remove it. If it’s an unnecessary modifier, I remove the modifier. Similarly, I address other issues by applying the recommended fixes accordingly.

## CI/CD

The current CI/CD workflows have met their respective definitions. The CI workflow includes unit tests, while the CD workflow is managed by Koyeb. To maintain code quality across all branches, the CI workflow runs tests automatically on every push and pull request to the repository. However, Koyeb will only deploy from the master branch, ensuring that only approved changes reach production. If the CI tests fail, Koyeb will block the deployment, preventing unstable code from being released.

</details>

<details>
<summary>
  :two: Module 3
</summary>

## Explain what principles you apply to your project!

### Single-responsibility Principle
1. **Separation of Controllers** &mdash; Before implementing SOLID, the `CarController` class is under `ProductController`. However, this is incorrect, because a class should only be responsible to one thing. To fix this, I separated `CarController` and `ProductController`.
2. **Move Logging to a Service** &mdash; Initially, the `CarController` class handled logging directly by printing messages to the command line. This was incorrect because logging is not the responsibility of a controller. To address this, I created a separate service, `LogConsoleService`, which handles all logging operations. This keeps the controller focused solely on handling HTTP requests.

### Open-closed Principle
1. **Logging Service** &mdash; As I said above, I implemented a log service called `LogConsoleService`. However, this is not the only possible logging service. There may be other logging service, such as `LogFileService` to log messages to a specified file on the server, or maybe `LogSlackService` to send logs to a Slack channel. To address this, I created an interface called `LogService`. This interface defines a consistent API for logging operations. Any logging service that needs to be added in the future can implement this interface without modifying the existing code, making the system open for extension but closed for modification.

### Liskov Substitution Principle
1. **Separate Car Controller and Product Controller** &mdash; Before applying SOLID, the `CarController` class is a subclass of `ProductController`. This meant that replacing `ProductController` with `CarController` would lead to incorrect behavior. To resolve this, I remove the inheritance of `CarController` to `ProductController`, ensuring that each controller functions independently and adheres to its intended purpose.

### Interface Segregation Principle
1. **Separate Repository** &mdash; To follow the Interface Segregation Principle (ISP), I divided the repository into two separate interfaces: WritableRepository and ReadonlyRepository. This ensures that each interface has a specific purpose, preventing classes from being forced to implement unnecessary methods. By breaking the repository into smaller, more focused interfaces, the code becomes easier to maintain, extend, and modify without affecting unrelated functionality.

### Dependency Inversion Principle
1. **Decoupling Car Controller** &mdash; Before applying SOLID principles, I noticed that in `CarController`, the car service was declared as type `CarServiceImpl`. This tightly coupled the controller to a specific implementation, making it difficult to test and maintain. To fix this, I simply changed the variable declaration to use the type `CarService`. This allows flexibility, making it easier to swap out different implementations (e.g. `CarServiceMock` for testing).

## Explain the advantages of applying SOLID principles to your project with examples.
Applying SOLID principles makes the project easier to maintain and modify.

For example, I have a service for logging to console, `LogConsoleService`. Because of the Single-responsibility Principle, modifying how logs are formatted is straightforward. If I need to add more context to the logs—such as timestamps, log levels, or request details—I can do so by updating LogConsoleService without affecting other parts of the application. This prevents code duplication and reduces the risk of inconsistencies.

Further more, because of the Open-Closed Principle, I can introduce additional logging services, such as `LogFileService` for writing logs to a file or `LogSlackService` for sending logs to a Slack channel, without modifying existing code. These new services simply implement the `LogService` interface, making it easy to swap or extend logging functionality.

Additionally, applying the Dependency Inversion Principle (DIP) ensures that CarController and other components depend on the LogService abstraction rather than a specific implementation. This makes testing easier since I can inject a mock logging service instead of relying on actual console output.

## Explain the disadvantages of not applying SOLID principles to your project with examples.

1. **Hard to Test** &mdash; Without dependency inversion, unit testing becomes difficult because dependencies cannot be easily replaced with mocks.
2. **More Code Duplication** &mdash; Violation to the SRP, multiple parts of the codebase may handle the same functionality (such as logging). This increases maintanence effort and the risk of inconsistencies.
3. **Difficult to Extend** &mdash; Without OCP, logging service is not as glamorous as it currently is. Adding a new logging service should be as simple as creating a new class that implements LogService. However, without OCP, introducing additional logging services would require modifying existing code, making the system more error-prone and inconsistent.
4. **Difficult to Modify** &mdash; With SRP, each class has a single, well-defined responsibility, making the codebase more understandable and maintainable. When responsibilities are clearly separated, it's easy to locate where a specific functionality belongs, reducing confusion and making future modifications straightforward. Without SRP, a class might take on multiple responsibilities, leading to unclear boundaries. This makes it harder to determine what a class is supposed to do, making modifications more challenging.

</details>

<details>
<summary>
  :four: Module 4
</summary>

## Reflection on TDD Flow Based on Percival (2017)

The TDD flow has been quite useful in guiding development, ensuring that each feature is well-defined before implementation. Writing tests first helped clarify requirements and prevented unnecessary code. However, there were moments when I wrote part of the implementation before creating tests, which slightly reduced the benefits of strict TDD. In the future, I need to be more disciplined about writing a failing test first before adding any functionality.

One challenge I encountered was occasionally skipping the refactor step after passing a test. While the initial implementation worked, some areas could have been optimized further. Next time, I will make a habit of reviewing the code after each green test to ensure it remains clean and maintainable. Additionally, while the tests provided confidence in correctness, I need to improve coverage for edge cases to handle unexpected inputs better.

## Evaluation of F.I.R.S.T. Principles

1. **First** &mdash; The tests execute quickly, with CI runs averaging under 1 minute and unit tests completing in around 30 seconds. This ensures they do not disrupt the development workflow.
2. **Isolated/Independent** &mdash; Each test runs independently without relying on others, ensuring that failures are localized and do not cascade.
3. **Repeatable** &mdash; Tests produce consistent results across different environments. They pass reliably on both my Windows machine and the CI server running Ubuntu.
4. **Self-Validating** &mdash; Clear and meaningful assertions ensure that test results are immediately actionable, simplifying debugging and reducing ambiguity.
5. **Thorough/Timely** &mdash; Tests are written before implementation, covering all critical paths, including both happy and edge cases, to ensure comprehensive validation.

Overall, my tests follow the F.I.R.S.T. principles well, making them reliable and efficient. They run quickly, don’t depend on each other, and give clear results, which makes debugging easier. Writing tests before implementation has also helped ensure good coverage. Moving forward, I’ll keep refining my approach to maintain a solid testing foundation.

</details>
