function [] = matlabVisualization()
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here

fun = @dejong5fcn;
x0 = [0 0];
x = simulannealbnd(fun,x0)

dejong5fcn

hold on;
scatter3(x(1), x(2), fun(x));

end

