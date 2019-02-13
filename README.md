# WhitePages DataService Exercise 

Please see the [wiki](https://github.com/eepstein/wp-ds-ex/wiki) for project design and plans.

In particular [the "Naive Approach"](https://github.com/eepstein/wp-ds-ex/wiki/Naive-Approach) page diagrams the 
currently implemented data model as well as a proposed more fully normalized enhancement. 

## Goal 1

Discover insights into population distribution and density.


## Goal 2

Create contact lists for subsets of the population, generally based on location.


## Approach

### Analysis

For the analysis:

* Center-of-mass
* k-means clustering

The anticipation is the latter will yield a set of regions with high population density. 

The k-means algorithm is "unsupervised" and so no training or previous knowledge of the data are required.

Spark (and other) frameworks offer libraries with k-means and it is available for use from both Scala and Python.


### Loading data

Since the analysis will require some form of distributed learning to perform the unsupervised k-means 
clustering analysis, we may as well take advantage of those tools.

Spark's support for `CSV` files (`spark-csv_2.aa-x.y.z.jar`) and for SQL, including joining across dataframes 
(via `spark-sql_2.bb-p.d.q.jar`) makes it a good choice, provided the input data are either canonicalized, or
the parsing of the data is customized so that it includes a canonicalization step prior to the join operations.

#### Alternative

Given the size of the data sets (~10MM records) and the processing time-frames (1+ weeks), the data loading 
can be handled without resort to distributed computing.  A simple loader with batch functionality, 
in-memory caching of names and current process sets, plus a bit of tuning (e.g., batch INSERT at 1,000 
records) would easily handle the data in a matter of hours.  

### Data enhancement

In order to perform geographical analysis, the data needs geo-coordinates (lat./long.) and those will
presumably need to be added.  In the USA those should generally be easy to retrieve and thus a process
can be crafted to lookup geo coordinates by some address information.  Depending on the granularity of the 
analysis, it may be sufficient to do so by, say zip-code, rather than on a per-address basis.

## Addenda

The wiki discusses, but does not fully address the issue of data management, in particular the issues of 
duplicate records and the related issue of disambiguation.

For a "v1" or "beta" version the proposal is to start with the data as-is and process it naively by:

1. Converting each kind of data (email, phone, address) into a canonical form;
2. Matching directly on the canonical form.

If needed, further analysis can then be performed to identify duplicates or ambiguities, for example around 
the spelling names.


