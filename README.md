# proletariat

This is a very basic framework in Clojure for distributing work via RabbitMQ. As it is now, all jobs are assumed to result in side effects (e.g., storing something to a database, generating more messages, etc.).

The starting point for this framework was Chapter 11 of Amit Rathore's excellent [Clojure in Action](http://www.manning.com/rathore/).

To run the tests, you will need to have RabbitMQ running locally.

# Installation

For now, clone this repo and install it locally with `lein install`.

Then add `[proletariat "0.0.1-SNAPSHOT"]` to your project dependencies.


# License & Author

Copyright: 02011, J.D. Hollis

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
