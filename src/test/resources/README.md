# Test Resources

When testing your Gatling code, you write your tests in the `test` source tree.  Most of the time, you want all the same configuration as you have for your integration tests.  For that reason, everything (save this file) is symlinked to the corresponding file in the `it` folder.  If you change a file here, you change it in both places.

If you ever need to have a different configuration for `test` than you have for `it`, replace the symlink with a copy of the file, and then customize it.