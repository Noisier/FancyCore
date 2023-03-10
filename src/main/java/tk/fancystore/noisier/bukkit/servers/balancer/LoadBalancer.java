package tk.fancystore.noisier.bukkit.servers.balancer;

import tk.fancystore.noisier.bukkit.servers.balancer.elements.LoadBalancerObject;

public interface LoadBalancer<T extends LoadBalancerObject> {
  T next();
}
