# IntlRateSearch
An example project inspired by 7L Freight's airline rate search tool.

I initially set out to create an alternative to 7L for my office - my thoughts being that it would both save us the cost of the service subscription and the occasional headache of running across out-of-date information that we couldn't change ourselves - running off of a local database would mean we could immediately make our own changes.

As it turns out, the time commitment required just to set up and maintain the database would absolutely dwarf the time required to write the program itself, so I abandoned my original intent in favor of just using this as my sample program for Launch Code.

Requires the following sqlite database to be present in /src, since git doesn't seem to like the file type:
https://www.dropbox.com/s/pott0p4y3ludge7/airRateDB.db?dl=0

Please note, the database is a rather barebones example, with only a few airlines and a handful of airport codes included.
Usable origin/destination pairs are JFK-ABZ and ORD-PVG.
