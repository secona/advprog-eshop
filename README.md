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
