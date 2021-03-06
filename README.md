# kafka-template

 simple app template to create kafka clients using core.async

## Usage

Just instantiate `producer!`s and `consumer!`s when you need.
Both those functions return a `core.async/chan`.

To send send a message to topic, send a message to the producer channel returned by `producer!`.

To receive a message from a topic, read the messages on the consumer channel returned by `consumer!`.

There is also a `worker!` facility that applies consumes a consumer channel and apply a function to each message received.

To run the example just:
```bash
lein run
```

## License

Copyright © 2021 Lucas Severo.

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
