#!/usr/bin/env python
# Correlation coefficients for two paired vectors
# 
# Kyle Gorman <kgorman@ling.upenn.edu>
# 
# USER FUNCTIONS: 
# pearson_rho: Pearson's product-moment linear correlation coefficient
# spearman_rho: Spearman's rank correlation coefficient
# spearman_rho_tr: A rank correlation coefficient for tied-rank data
# goodman_krusal_gamma: A rank correlation coefficient by Goodman and Kruskal
#                       that simply ignores ties
# kendall_tau_b: Kendall's rank correlation coefficient for tied-rank data 
# kendall_tau_c: Kednall's _other_ rank correlation coefficient for tied data

from math import sqrt, fsum
from operator import itemgetter
from itertools import combinations, permutations

## HELPER FUNCTIONS

def _mean(m):
    return fsum(m) / len(m)


def _fancy(m):
    a = sum(m)
    b = 0
    for i in m:
        b += i * i
    return len(m) * b - (a ** 2)


def _rank(m):
    (ivec, svec) = zip(*sorted(list(enumerate(m)), key=itemgetter(1)))
    sumranks = 0
    dupcount = 0
    newlist = [0] * len(m)
    for i in range(len(m)):
        sumranks += i
        dupcount += 1
        if i == len(m) - 1 or svec[i] != svec[i + 1]:
            averank = sumranks / float(dupcount) + 1
            for j in range(i - dupcount + 1, i + 1):
                newlist[ivec[j]] = averank
            sumranks = 0
            dupcount = 0
    return newlist


def _concordance(m, n):
    """ 
    returns count of concordant, discordant, and tied pairs
    """
    if len(m) != len(n):
        raise ValueError, 'Iterables (m, n) must be the same length'
    c = 0
    d = 0
    iss = 0
    for (i, j) in combinations(xrange(len(m)), 2):
        m_dir = m[i] - m[j]
        n_dir = n[i] - n[j]
        sign = m_dir * n_dir
        if sign: # not a tie
            c += 1
            d += 1
            if sign > 0:
                iss += 1
            elif sign < 0:
                iss -= 1
        else:
            if m_dir:
                c += 1
            elif n_dir:
                d += 1
            # else is a tie in both ways and of no concern to us
    return (c, d, iss)


## USER FUNCTIONS

def pearson_rho(m, n):
    """ 
    return the Pearson rho coefficient; based off stats.py 

    >>> x = [2, 8, 5, 4, 2, 6, 1, 4, 5, 7, 4]
    >>> y = [3, 9, 4, 3, 1, 7, 2, 5, 6, 8, 3]
    >>> pearson_rho(x, y)
    0.9245404356092288
    """
    if len(m) != len(n):
        raise ValueError, 'Iterables (m, n) must be the same length'
    num = len(m) * (sum([i * j for i, j in zip(m, n)])) - sum(m) * sum(n)
    return num / sqrt(_fancy(m) * _fancy(n))


def spearman_rho(m, n):
    """ 
    return Spearman's rho; based off stats.py 

    >>> x = [2, 8, 5, 4, 2, 6, 1, 4, 5, 7, 4]
    >>> y = [3, 9, 4, 3, 1, 7, 2, 5, 6, 8, 3]
    >>> spearman_rho(x, y)
    0.9363636363636364
    """
    if len(m) != len(n):
        raise ValueError, 'Iterables (m, n) must be the same length'
    dsq = sum([(mi - ni) ** 2 for (mi, ni) in zip(_rank(m), _rank(n))])
    return 1. - 6. * dsq / float(len(m) * (len(n) ** 2 - 1.))


def spearman_rho_tr(m, n):
    """ 
    rho for tied ranks, checked by comparison with Pycluster

    >>> x = [2, 8, 5, 4, 2, 6, 1, 4, 5, 7, 4]
    >>> y = [3, 9, 4, 3, 1, 7, 2, 5, 6, 8, 3]
    >>> spearman_rho_tr(x, y)
    0.9348938334114621
    """
    m = _rank(m)
    n = _rank(n)
    num = 0.
    den_m = 0.
    den_n = 0.
    m_mean = _mean(m)
    n_mean = _mean(n) 
    for (i, j) in zip(m, n):
        i = i - m_mean
        j = j - n_mean
        num += i * j 
        den_m += i ** 2
        den_n += j ** 2
    return num / sqrt(den_m * den_n)


def goodman_kruskal_gamma(m, n):
    """ 
    compute the Goodman and Kruskal gamma rank correlation coefficient; 
    this statistic ignores ties is unsuitable when the number of ties in the
    data is high. it's also slow. 

    >>> x = [2, 8, 5, 4, 2, 6, 1, 4, 5, 7, 4]
    >>> y = [3, 9, 4, 3, 1, 7, 2, 5, 6, 8, 3]
    >>> goodman_kruskal_gamma(x, y)
    0.9166666666666666
    """
    num = 0
    den = 0
    for (i, j) in permutations(xrange(len(m)), 2):
        m_dir = m[i] - m[j]
        n_dir = n[i] - n[j]
        sign = m_dir * n_dir
        if sign > 0:
            num += 1
            den += 1
        elif sign < 0:
            num -= 1
            den += 1
    return num / float(den)


def kendall_tau_b(m, n):
    """ 
    compute Kendall's rank correlation coefficient tau_b; based on stats.py,
    but fixes a major bug in that code (as well as the scipy implementation); 
    the results returned here accord with STATA/SPSS/SAS/R 

    >>> x = [2, 8, 5, 4, 2, 6, 1, 4, 5, 7, 4]
    >>> y = [3, 9, 4, 3, 1, 7, 2, 5, 6, 8, 3]
    >>> kendall_tau_b(x, y)
    0.8629109946080097
    """
    (c, d, iss) = _concordance(m, n)
    return iss / sqrt(c * d)


def kendall_tau_c(m, n):
    """ 
    compute Kendall's rank correlation coefficient tau_c

    >>> x = [2, 8, 5, 4, 2, 6, 1, 4, 5, 7, 4]
    >>> y = [3, 9, 4, 3, 1, 7, 2, 5, 6, 8, 3]
    >>> kendall_tau_c(x, y)
    0.8484848484848485
    """
    (c , d, iss) = _concordance(m, n)
    min_dim = min(len(set(m)), len(set(n)))
    return (2. * min_dim * iss) / ((min_dim - 1) * len(m) ** 2)


if __name__ == '__main__':
    from doctest import testmod
    testmod()
